package com.example.myapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapp.R
import com.example.myapp.adapters.TrainingsAdapter
import com.example.myapp.data.Database
import com.example.myapp.databinding.FragmentAllTrainingsBinding
import com.example.myapp.models.TrainingModel
import com.example.myapp.repositories.TrainingRepository
import com.example.myapp.viewModels.TrainingFactory
import com.example.myapp.viewModels.TrainingViewModel

class AllTrainingsFragment : Fragment() {

    private lateinit var binding: FragmentAllTrainingsBinding
    private lateinit var trainingViewModel: TrainingViewModel
    private lateinit var trainingFactory: TrainingFactory
    private lateinit var trainingAdapter: TrainingsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllTrainingsBinding.inflate(inflater,container, false )

        val trainingsDao = Database.getInstance((context as FragmentActivity).application).trainingDAO
        val trainingRepository = TrainingRepository(trainingsDao)
        trainingFactory = TrainingFactory(trainingRepository)
        trainingViewModel = ViewModelProvider(this, trainingFactory).get(TrainingViewModel::class.java)
        initRecyclerTrainings()
        displayTrainings()
        binding.createNewTrain.setOnClickListener {
            val panelNewTrain = TrainCreationFragment()
            panelNewTrain.show((context as FragmentActivity).supportFragmentManager, "editCount")

        }
        return binding.root
    }

    private fun initRecyclerTrainings(){
        binding.recyclerCategories.layoutManager = LinearLayoutManager(context)
        // todo: кажется адаптер должен вызывать только методы view model, но не факт
        trainingAdapter = TrainingsAdapter({categoryModel: TrainingModel -> deleteTraining(categoryModel)},
            {categoryModel: TrainingModel -> editTraining(categoryModel) },
            {categoryModel: TrainingModel -> createPost(categoryModel)})
        binding.recyclerCategories.adapter = trainingAdapter
    }

    private fun displayTrainings(){
        trainingViewModel.getAllTrainings()
        trainingViewModel.nameTraining.setValue("gsdfdsf")
        trainingViewModel.allTrainings.observe(viewLifecycleOwner, Observer {
            trainingAdapter.setList(it)
            trainingAdapter.notifyDataSetChanged()
        })

    }

    private fun deleteTraining(trainingModel: TrainingModel) {
        trainingViewModel.deleteTraining(trainingModel)
    }
    private fun createPost(trainingModel: TrainingModel) {
        //trainingViewModel.deleteTraining(trainingModel)
        //Toast.makeText(context, trainingModel.name, Toast.LENGTH_SHORT).show()
        val fragment = PostCreationFragment()
        val parameters = Bundle()
        parameters.putInt("idTraining", trainingModel.id)
        fragment.arguments = parameters
        fragment.show((context as FragmentActivity).supportFragmentManager, "createPost")
    }

    private fun editTraining(trainingModel: TrainingModel) {
        val fragment = TrainCreatorFragment()
        val parameters = Bundle()
        parameters.putInt("idTraining", trainingModel.id)
        parameters.putString("nameTraining", trainingModel.name)
        //parameters.putString("nameExercise", trainingModel.exercise.name)
        //parameters.putString("count", trainingModel.playlist.count.toString())
//        parameters.putString("muscleGroupExercise", trainingModel.muscle_group)
        fragment.arguments = parameters
        val transaction  = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.content, fragment)
        transaction?.addToBackStack(null)
        transaction?.commit()

    }



    companion object {
        @JvmStatic
        fun newInstance() = AllTrainingsFragment()
    }
}