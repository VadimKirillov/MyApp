package com.example.myapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapp.adapters.ExerciseAdapter
import com.example.myapp.adapters.PickExerciseToTrainAdapter
import com.example.myapp.data.Database
import com.example.myapp.databinding.FragmentPickExerciseToTrainBinding
import com.example.myapp.fragments.CreationExerciseFragment
import com.example.myapp.fragments.EditExerciseFragment
import com.example.myapp.fragments.TrainCreatorFragment
import com.example.myapp.models.ExerciseModel
import com.example.myapp.repositories.ExerciseRepository
import com.example.myapp.viewModels.ExerciseFactory
import com.example.myapp.viewModels.ExerciseViewModel

class PickExerciseToTrainFragment : Fragment() {
        private lateinit var binding: FragmentPickExerciseToTrainBinding
        private lateinit var exerciseRepository: ExerciseRepository
        private lateinit var exerciseViewModel: ExerciseViewModel
        private lateinit var pickExerciseToTrainAdapter: PickExerciseToTrainAdapter

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            val group = arguments?.getString("filter")
            Log.d("debug", group?.toString() ?: "")
            binding = FragmentPickExerciseToTrainBinding.inflate(inflater, container, false)
            val exercisesDao = Database.getInstance((context as FragmentActivity).application).exerciseDAO
            exerciseRepository = ExerciseRepository(exercisesDao)
            val exerciseFactory = ExerciseFactory(exerciseRepository)
            exerciseViewModel = ViewModelProvider(this, exerciseFactory).get(ExerciseViewModel::class.java)
            exerciseViewModel.filterName.setValue("%%");
            exerciseViewModel.filterGroup.setValue(group);
            exerciseViewModel.initExercises()
            val trainingId = arguments?.getInt("idTraining")
            val trainingName = arguments?.getString("nameTraining")


            binding.addExercisesToTraining.setOnClickListener {
                addExercisesToTraing(exerciseViewModel.selectedExercises, trainingId!!)
                val trainCreatorFragment = TrainCreatorFragment()
                val arguments = Bundle()
                arguments.putInt("idTraining", trainingId)
                arguments.putString("nameTraining", trainingName!!)
                trainCreatorFragment.arguments = arguments
                (context as FragmentActivity).supportFragmentManager.beginTransaction().replace(R.id.content, trainCreatorFragment).commit()

                //fragmentManager?.popBackStack()

            }

            initRecyclerExercises()
            displayExercises()

            return binding.root
        }

        private fun initRecyclerExercises(){
            binding.recyclerCategories.layoutManager = LinearLayoutManager(context)
            pickExerciseToTrainAdapter = PickExerciseToTrainAdapter {
             categoryModel: ExerciseModel, state : Boolean ->
                pickExercise(
                    categoryModel, state
                )
            }
            binding.recyclerCategories.adapter = pickExerciseToTrainAdapter
        }

        private fun displayExercises(){
            exerciseViewModel.exercises.observe(viewLifecycleOwner, Observer {
                pickExerciseToTrainAdapter.setList(it)
                pickExerciseToTrainAdapter.notifyDataSetChanged()
            })
        }

        private fun pickExercise(exerciseModel: ExerciseModel, state :Boolean) {
            // todo: уберём, в тренировке будем добавлять упражнения
            if (state){
                exerciseViewModel.selectedExercises.add(exerciseModel)
            }
            else {
                exerciseViewModel.selectedExercises.remove(exerciseModel)
            }
        }

        private fun addExercisesToTraing(selectedExercises: List<ExerciseModel>, trainingId:Int){
            for (selectedExercise in selectedExercises){
                exerciseViewModel.pickExercise(selectedExercise, trainingId)
            }
        }

        companion object {
            @JvmStatic
            fun newInstance() = PickExerciseToTrainFragment()
        }
    }