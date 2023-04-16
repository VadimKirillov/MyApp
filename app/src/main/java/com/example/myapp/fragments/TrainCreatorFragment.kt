package com.example.myapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.lifecycle.Observer
import com.example.myapp.PickExerciseToTrainFragment
import com.example.myapp.R
import com.example.myapp.data.Database
import com.example.myapp.databinding.FragmentTrainCreatorBinding
import com.example.myapp.models.LineWithExercises
import com.example.myapp.repositories.TrainingRepository
import com.example.myapp.adapters.TrainingAdapter
import com.example.myapp.viewModels.TrainingFactory
import com.example.myapp.viewModels.TrainingViewModel


class TrainCreatorFragment : Fragment() {
    private lateinit var binding: FragmentTrainCreatorBinding

    private lateinit var trainingViewModel: TrainingViewModel
    private lateinit var trainingFactory: TrainingFactory
    private lateinit var trainingAdapter: TrainingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrainCreatorBinding.inflate(inflater,container, false )
        val id = arguments?.getInt("idTraining")

        val trainingsDao = Database.getInstance((context as FragmentActivity).application).trainingDAO
        val trainingRepository = TrainingRepository(trainingsDao)
        trainingFactory = TrainingFactory(trainingRepository)
        trainingViewModel = ViewModelProvider(this, trainingFactory).get(TrainingViewModel::class.java)
        trainingViewModel.getTrainingWithExercisesById(id)

        binding.addExercise.setOnClickListener {
            (context as FragmentActivity).supportFragmentManager.beginTransaction().replace(R.id.content, PickExerciseToTrainFragment()).commit()
        }
        initRecyclerTrainings()
        displayTrainings()

        return binding.root
    }

    private fun initRecyclerTrainings(){
        binding.recyclerCategories.layoutManager = LinearLayoutManager(context)
        // todo: кажется адаптер должен вызывать только методы view model, но не факт
        trainingAdapter = TrainingAdapter({categoryModel: LineWithExercises -> deleteTraining(categoryModel)},
            {categoryModel: LineWithExercises -> editTraining(categoryModel)})
        binding.recyclerCategories.adapter = trainingAdapter
    }

    private fun displayTrainings(){
        trainingViewModel.trainings
        trainingViewModel.trainings.observe(viewLifecycleOwner, Observer {
            trainingAdapter.setList(it.get(0).lines)
            trainingAdapter.notifyDataSetChanged()
        })
    }


    private fun deleteTraining(trainingModel: LineWithExercises) {
        trainingViewModel.deleteLine(trainingModel)
    }


    private fun editTraining(trainingModel: LineWithExercises) {
        val panelEditCountTrain = EditCountTrainFragment()
        val parameters = Bundle()
        parameters.putString("idExercise", trainingModel.exercise.id.toString())
        parameters.putString("nameExercise", trainingModel.exercise.name)
        parameters.putString("count", trainingModel.playlist.count.toString())
//        parameters.putString("muscleGroupExercise", trainingModel.muscle_group)
        panelEditCountTrain.arguments = parameters
        panelEditCountTrain.show((context as FragmentActivity).supportFragmentManager, "editCount")
    }
    
    companion object {
        @JvmStatic
        fun newInstance() = TrainCreatorFragment()
    }


}