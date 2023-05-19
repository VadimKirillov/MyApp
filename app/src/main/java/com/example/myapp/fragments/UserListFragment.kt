package com.example.myapp.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.adapters.ExerciseGlobalAdapter
import com.example.myapp.data.Database
import com.example.myapp.data.model.LoggedInUser
import com.example.myapp.databinding.ListExercisesGlobalBinding
import com.example.myapp.models.ExerciseModel
import com.example.myapp.repositories.ExerciseRepository
import com.example.myapp.viewModels.ExerciseFactory
import com.example.myapp.viewModels.ExerciseViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import androidx.lifecycle.Observer

class UserListFragment : Fragment() {
    private lateinit var binding: ListExercisesGlobalBinding
    private lateinit var exerciseRepository: ExerciseRepository
    private lateinit var exerciseViewModel: ExerciseViewModel
    private lateinit var exerciseAdapter: ExerciseGlobalAdapter
    private var isLoading = false
    private var currentPage = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var group = arguments?.getString("filter")

        //Log.d("debug", group?.toString() ?: "")
        binding = ListExercisesGlobalBinding.inflate(inflater, container, false)

//        if(group == "Все" || group == null){
//            binding.textHeaderGroup.text = "Все"
//            group = null
//        }
//        else{
//            binding.textHeaderGroup.text = group
//        }

        val exercisesDao = Database.getInstance((context as FragmentActivity).application).exerciseDAO
        exerciseRepository = ExerciseRepository(exercisesDao)
        val exerciseFactory = ExerciseFactory(exerciseRepository)
        exerciseViewModel = ViewModelProvider(this, exerciseFactory).get(ExerciseViewModel::class.java)

        initRecyclerExercises()
        exerciseViewModel.filterName.setValue("%%");
        exerciseViewModel.filterGroup.setValue(group);
        displayExercises()

        binding.deleteSearch.setOnClickListener {
            binding.searchExercise.setText("")
        }

        binding.searchExercise.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {

                    exerciseViewModel.filterName.
                    setValue("%" + s.toString() + "%");
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })
        return binding.root
    }

    private fun initRecyclerExercises(){
        binding.recyclerCategories.layoutManager = LinearLayoutManager(context)
//        exerciseAdapter = ExerciseGlobalAdapter(
//            {user: LoggedInUser -> showProfile(user)}
//        )
        binding.recyclerCategories.adapter = exerciseAdapter
        binding.recyclerCategories.addOnScrollListener(scrollListener)
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

    private fun displayExercises(){
        exerciseViewModel.initExercisesRemote()
        exerciseViewModel.exercises.observe(viewLifecycleOwner, Observer {
            Log.e("Paging ", "PageAll" + it.size);

            exerciseAdapter.submitList(it)
        })
    }

    private fun showProfile(user: LoggedInUser) {
        GlobalScope.launch {
            // todo: фрагмент
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() = ExerciseFragment()
    }
}