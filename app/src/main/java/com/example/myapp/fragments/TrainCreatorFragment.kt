package com.example.myapp.fragments

import android.os.Bundle
import android.util.Log
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
    private var idTraining: Int = 0
    private var nameTraining: String = " "

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrainCreatorBinding.inflate(inflater,container, false )
        idTraining = arguments?.getInt("idTraining")!!
//        nameTraining = arguments?.getString("nameTraining")!!
        binding.textView13.setText(nameTraining)
        val trainingsDao = Database.getInstance((context as FragmentActivity).application).trainingDAO
        val trainingRepository = TrainingRepository(trainingsDao)
        trainingFactory = TrainingFactory(trainingRepository)
        trainingViewModel = ViewModelProvider(this, trainingFactory).get(TrainingViewModel::class.java)
        trainingViewModel.getTrainingWithExercisesById(idTraining)

        binding.addExercise.setOnClickListener {

            val transaction  = activity?.supportFragmentManager?.beginTransaction()
            val parameters = Bundle()
            parameters.putInt("idTraining", idTraining)
            parameters.putString("nameTraining", nameTraining)
            val pickExerciseToTrainFragment = PickExerciseToTrainFragment()
            pickExerciseToTrainFragment.arguments = parameters
            transaction?.replace(R.id.content, pickExerciseToTrainFragment)
            transaction?.addToBackStack(null)
            transaction?.commit()


        }
        initRecyclerTrainings()
        displayTrainingsLines()

        return binding.root
    }

    private fun initRecyclerTrainings(){
        binding.recyclerCategories.layoutManager = LinearLayoutManager(context)
        // todo: кажется адаптер должен вызывать только методы view model, но не факт
        trainingAdapter = TrainingAdapter({categoryModel: LineWithExercises -> deleteTraining(categoryModel)},
            {categoryModel: LineWithExercises -> editTraining(categoryModel)})
        binding.recyclerCategories.adapter = trainingAdapter
    }

    private fun displayTrainingsLines(){
        Log.d("banban", trainingViewModel.allTrainings.toString())
        Log.d("banban", trainingViewModel.linesLiveData.toString())
//        trainingViewModel.trainings.observe(viewLifecycleOwner, Observer {
//            Log.d("banban", it.size.toString())
//            trainingAdapter.setList(trainingViewModel.linesLiveData.value)
//            trainingAdapter.notifyDataSetChanged()
//        })
        trainingViewModel.linesLiveData.observe(viewLifecycleOwner, Observer {
            Log.d("banban", it.size.toString())
            trainingAdapter.setList(it)
            trainingAdapter.notifyDataSetChanged()
      })

    }


    private fun deleteTraining(trainingModel: LineWithExercises) {
        trainingViewModel.deleteLine(trainingModel)
    }


    private fun editTraining(trainingModel: LineWithExercises) {
        val panelEditCountTrain = EditCountTrainFragment()
        val parameters = Bundle()
        parameters.putInt("idTrainLine", trainingModel.playlist.id)
        parameters.putInt("idExercise", trainingModel.playlist.exercise_id)
        parameters.putInt("idTraining", idTraining)
        parameters.putString("nameTraining", nameTraining)
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