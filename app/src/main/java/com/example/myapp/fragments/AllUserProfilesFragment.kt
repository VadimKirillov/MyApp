package com.example.myapp.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.paging.DataSource
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.PageKeyedDataSource
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apollographql.apollo3.api.Optional.Companion.present
import com.example.myapp.R
import com.example.myapp.adapters.ExerciseGlobalAdapter
import com.example.myapp.data.Database
import com.example.myapp.data.UserProfile
import com.example.myapp.data.model.UtilClient
import com.example.myapp.databinding.FragmentAllUserProfilesBinding
import com.example.myapp.databinding.ListExercisesGlobalBinding
import com.example.myapp.models.ExerciseModel
import com.example.myapp.repositories.ExerciseRepository
import com.example.myapp.viewModels.ExerciseFactory
import com.example.myapp.viewModels.ExerciseViewModel
import com.example.type.UserInput
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import com.apollographql.apollo3.api.Optional
import com.example.AllExercicesQuery
import com.example.SearchUsersQuery
import com.example.myapp.adapters.AllUsersProfilesAdapter
import com.example.myapp.adapters.GroupExerciseAdapter
import com.example.myapp.data.GroupExercise
import com.example.myapp.utils.Converter
import com.example.myapp.viewModels.PostsDataSource
import com.example.type.Sort
import com.example.type.UserOrderByInput

class AllUserProfilesFragment : Fragment(), AllUsersProfilesAdapter.Listener {
    private lateinit var binding: FragmentAllUserProfilesBinding
    private var isLoading = false
    private var currentPage = 0
    private lateinit var userAdapter: AllUsersProfilesAdapter
    lateinit var users : LiveData<PagedList<UserProfile>>

    var config : PagedList.Config
        init {
            config = PagedList.Config.Builder()
                .setPageSize(100)
    //            .setEnablePlaceholders(false)
                .build()
        }


    var filterName = MutableLiveData<String>()
    var filterGroup = MutableLiveData<String>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllUserProfilesBinding.inflate(inflater, container, false)
        initRecyclerExercises()
        displayUsers()
        filterName.setValue("%%")
        return binding.root
    }

    override fun onClick(user: UserProfile) {
        super.onClick(user)
        Toast.makeText(context, "Переход", Toast.LENGTH_LONG).show()

//        val transaction  = activity?.supportFragmentManager?.beginTransaction()
//        val parameters = Bundle()
//        parameters.putString("filter", groupExercise.title)
//        Log.d("debug", groupExercise.title)
//        val fragment = ExerciseFragment()
//        fragment.arguments = parameters
//        transaction?.replace(R.id.content, fragment)
//        transaction?.addToBackStack(null)
//        transaction?.commit()
    }
    private fun initRecyclerExercises(){
        binding.recyclerCategories.layoutManager = LinearLayoutManager(context)
        userAdapter = AllUsersProfilesAdapter(
            {user:UserProfile-> openUser(user)}
        )
        binding.recyclerCategories.adapter = userAdapter
       // binding.recyclerCategories.addOnScrollListener(scrollListener)

    }

    private fun openUser(user: UserProfile){
        val userExercises = ExerciseGlobalFragment()
        val arguments = Bundle()
        arguments.putString("idUser", user.nickname)

        userExercises.arguments = arguments
        val transaction  = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.content, userExercises)
        transaction?.addToBackStack(null)
        transaction?.commit()

    }
    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                if (!isLoading) {
                    currentPage++
                }
            }
        }
    }

     private fun displayUsers(){
            initUsersRemote()
            users.observe(viewLifecycleOwner, Observer {
                Log.e("Paging ", "PageAll" + it.size);

                userAdapter.submitList(it)
            })
        }

     fun initUsersRemote(){

        val dataSourceFactory = object : DataSource.Factory<Integer, UserProfile>() {
            override fun create(): DataSource<Integer, UserProfile> {
                    return UserDataSource("")
                }
            }
         val source = LivePagedListBuilder<Integer, UserProfile>(dataSourceFactory, config)
         users = Transformations.switchMap(
               ExerciseViewModel.DoubleTrigger(filterName, filterGroup),
               { source.build()}
               )

    }



    companion object {
        @JvmStatic
        fun newInstance() = AllUserProfilesFragment()
    }
}


class UserDataSource(val filter: String) : PageKeyedDataSource<Integer, UserProfile>() {

    data class Response(
       val users: MutableList<UserProfile>,
       val position: Int,
       val total_count: Int
    )

    suspend fun query(filterName: String): Response {

            val client = UtilClient.instance
            var input = UserInput(
                order_by=Optional.present(
                    UserOrderByInput(nickname= Optional.present(Sort.valueOf("desc"))
                ))
            )

            Log.e("query ", "Query users")
            val response = client.apolloClient.query(
                SearchUsersQuery(
                    input
                )
            ).execute()

            var userList = mutableListOf<UserProfile>()
            if (response.data?.searchUsers?.users != null){
                for (line in response.data?.searchUsers?.users!!) {
                    userList.add(UserProfile(line?.nickname!!, line.picture))
                }
            }

            val data = UserDataSource.Response(
                userList,
                0,
                0,
            )
        Log.e("query ", "${userList.size}");
        Log.e("query ", "Query users end");
        return data
    }

    override fun loadInitial(
        params: LoadInitialParams<Integer>,
        callback: LoadInitialCallback<Integer, UserProfile>
    ) {
        GlobalScope.launch {
         // todo: пустой фильтр
            val data = query("")
            callback.onResult(data.users ?: listOf(),
                Integer(data.position),
                Integer(data.total_count)
            )
        }

        }


    override fun loadAfter(
        params: LoadParams<Integer>,
        callback: LoadCallback<Integer, UserProfile>
    ) {
            GlobalScope.launch {
                val data = query("")
                callback.onResult(listOf(),
                    Integer(data.position),
                )
            }


        }


    override fun loadBefore(
        params: LoadParams<Integer>,
        callback: LoadCallback<Integer, UserProfile>
    ) {

        GlobalScope.launch {
            val data = query("")
            callback.onResult(listOf(),
                Integer(data.position),
            )
        }
    }
}



