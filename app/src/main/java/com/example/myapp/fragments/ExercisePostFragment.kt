package com.example.myapp.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.lifecycle.Observer
import com.example.myapp.PostViewModel
import com.example.myapp.PostsFragment
import com.example.myapp.R
import com.example.myapp.adapters.ExercisePostAdapter
import com.example.myapp.data.Database
import com.example.myapp.databinding.FragmentTrainCreatorBinding
import com.example.myapp.models.LineWithExercises
import com.example.myapp.repositories.TrainingRepository
import com.example.myapp.adapters.TrainingAdapter
import com.example.myapp.databinding.FragmentExercisePostBinding
import com.example.myapp.models.TrainingModel
import com.example.myapp.viewModels.TrainingFactory
import com.example.myapp.viewModels.TrainingViewModel


class ExercisePostFragment : Fragment() {
    private lateinit var binding: FragmentExercisePostBinding

    private lateinit var trainingViewModel: TrainingViewModel
    private lateinit var trainingFactory: TrainingFactory
    private lateinit var trainingAdapter: ExercisePostAdapter
    private var idTraining: Int = 0
    private var nameTraining: String = " "

    private val postViewModel: PostViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExercisePostBinding.inflate(inflater,container, false )
        idTraining = arguments?.getInt("idTraining")!!
        nameTraining = arguments?.getString("nameTraining")!!
        var read = arguments?.getBoolean("read")
        //binding.editNameTraining.setText(nameTraining)
        val trainingsDao = Database.getInstance((context as FragmentActivity).application).trainingDAO
        val trainingRepository = TrainingRepository(trainingsDao)
        trainingFactory = TrainingFactory(trainingRepository)
        trainingViewModel = ViewModelProvider(this, trainingFactory).get(TrainingViewModel::class.java)
        if (read!!){
            trainingViewModel.getTrainingWithExercisesReadById(postViewModel.post)
            binding.namePostText.setText(postViewModel.post.postHead)
            binding.textView19.setText(postViewModel.post.text)
        }
        else{
            trainingViewModel.getTrainingWithExercisesById(idTraining)
        }
        trainingViewModel.nameTraining.setValue("testte")



        initRecyclerTrainings()
        displayTrainingsLines()

        return binding.root
    }

    private fun initRecyclerTrainings(){
        binding.recyclerCategories.layoutManager = LinearLayoutManager(context)
        // todo: кажется адаптер должен вызывать только методы view model, но не факт
        trainingAdapter = ExercisePostAdapter(trainingViewModel)

        binding.recyclerCategories.adapter = trainingAdapter

    }

    private fun displayTrainingsLines(){
        //       Log.d("banban", trainingViewModel.allTrainings.toString())
        Log.d("banban", trainingViewModel.linesLiveData.toString())
//        trainingViewModel.trainings.observe(viewLifecycleOwner, Observer {
//            Log.d("banban", it.size.toString())
//            trainingAdapter.setList(trainingViewModel.linesLiveData.value)
//            trainingAdapter.notifyDataSetChanged()
//        })
        trainingViewModel.linesLiveData.observe(viewLifecycleOwner, Observer {
            val sortedList = it.sortedWith(compareBy({ it.playlist.sequence }))
            // sequence
            Log.d("train_creator", it.size.toString())
            trainingAdapter.setList(sortedList)
            trainingAdapter.notifyDataSetChanged()
        })

    }



    companion object {
        @JvmStatic
        fun newInstance() = TrainCreatorFragment()
    }




}