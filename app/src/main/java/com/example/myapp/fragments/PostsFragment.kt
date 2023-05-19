package com.example.myapp

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
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.*
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apollographql.apollo3.api.Optional
import com.example.SearchTrainingsQuery
import com.example.myapp.adapters.AllUsersProfilesAdapter
import com.example.myapp.adapters.ExerciseGlobalAdapter
import com.example.myapp.adapters.PostsAdapter
import com.example.myapp.data.*
import com.example.myapp.data.model.UtilClient
import com.example.myapp.databinding.FragmentAllUserProfilesBinding
import com.example.myapp.databinding.FragmentPostsBinding
import com.example.myapp.databinding.ListExercisesGlobalBinding
import com.example.myapp.fragments.TrainCreatorFragment
import com.example.myapp.models.ExerciseModel
import com.example.myapp.repositories.ExerciseRepository
import com.example.myapp.viewModels.ExerciseFactory
import com.example.myapp.viewModels.ExerciseViewModel
import com.example.type.Sort
import com.example.type.TrainingInput
import com.example.type.UserInput
import com.example.type.UserOrderByInput
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

class PostsFragment : Fragment() {
    private lateinit var binding: FragmentPostsBinding
    private var isLoading = false
    private var currentPage = 0
    private lateinit var postAdapter: PostsAdapter
    lateinit var users : LiveData<PagedList<Post>>
    private val postViewModel: PostViewModel by activityViewModels()

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
        binding = FragmentPostsBinding.inflate(inflater, container, false)
        initRecyclerPosts()
        displayPosts()
        filterName.setValue("%%")

        binding.createNewPost.setOnClickListener {
            Toast.makeText(context,"Создание поста", Toast.LENGTH_SHORT).show()

        }
        return binding.root
    }

    private fun initRecyclerPosts(){
        binding.recyclerCategories.layoutManager = LinearLayoutManager(context)
        postAdapter = PostsAdapter({categoryModel:Post-> openPost(categoryModel)})
        binding.recyclerCategories.adapter = postAdapter
        // binding.recyclerCategories.addOnScrollListener(scrollListener)

    }
    private fun openPost(post: Post) {
//        GlobalScope.launch {
//            Toast.makeText(context, "ss", Toast.LENGTH_SHORT)
//        }
        Toast.makeText(context, "ss", Toast.LENGTH_SHORT).show()
        val fragment = TrainCreatorFragment()
        val parameters = Bundle()
        postViewModel.post = post


        parameters.putInt("idTraining", 1)
        parameters.putString("nameTraining", "Post")
        parameters.putBoolean("read", true)

        fragment.arguments = parameters
        val transaction  = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.content, fragment)
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

    private fun displayPosts(){
        initUsersRemote()
        users.observe(viewLifecycleOwner, Observer {
            Log.e("Paging ", "PageAll" + it.size);

            postAdapter.submitList(it)
        })
    }

    fun initUsersRemote(){

        val dataSourceFactory = object : DataSource.Factory<Integer, Post>() {
            override fun create(): DataSource<Integer, Post> {
                return PostDataSource("")
            }
        }
        val source = LivePagedListBuilder<Integer, Post>(dataSourceFactory, config)
        users = Transformations.switchMap(
            ExerciseViewModel.DoubleTrigger(filterName, filterGroup),
            { source.build()}
        )


    }



    companion object {
        @JvmStatic
        fun newInstance() = PostsFragment()
    }
}


class PostDataSource(val filter: String) : PageKeyedDataSource<Integer, Post>() {

    data class Response(
        val users: MutableList<Post>,
        val position: Int,
        val total_count: Int
    )

    suspend fun query(filterName: String): Response {

        val client = UtilClient.instance

        var input = TrainingInput()

        Log.e("query ", "Query posts")
        val response = client.apolloClient.query(
            SearchTrainingsQuery(
                input
            )
        ).execute()
        println("${response.data?.searchTrainings?.trainings}")
        var userList = mutableListOf<Post>()
        if (response.data?.searchTrainings?.trainings != null){
            for (line in response.data?.searchTrainings?.trainings!!) {
                val exercises = mutableListOf<ExerciseLine>()
                for (line in line!!.training_lines!!) {
                    try{
                    exercises.add(ExerciseLine(
                        Exercise(line!!.exercise!!.name!!, line!!.exercise!!.picture!!, line!!.exercise!!.class_exercise!!),
                        line.count!!
                    ))}
                    catch (e: NullPointerException){

                    }
                }
                userList.add(Post(line?.name!!, line.user!!.picture, line.text, author=line.user!!.nickname, lines = exercises, likes = null))
            }
        }

        val data = PostDataSource.Response(
            userList,
            0,
            0,
        )
        Log.e("query ", "${userList.size}");
        Log.e("query ", "Query users posts");
        return data
    }

    override fun loadInitial(
        params: LoadInitialParams<Integer>,
        callback: LoadInitialCallback<Integer, Post>
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


    override fun loadAfter(params: LoadParams<Integer>, callback: LoadCallback<Integer, Post>) {
        GlobalScope.launch {
            val data = query("")
            callback.onResult(data.users ?: listOf(),
                Integer(data.position),
            )
        }


    }


    override fun loadBefore(params: LoadParams<Integer>, callback: LoadCallback<Integer, Post>) {

        GlobalScope.launch {
            val data = query("")
            callback.onResult(data.users ?: listOf(),
                Integer(data.position),
            )
        }
    }
}
