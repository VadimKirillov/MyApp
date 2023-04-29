package com.example.myapp.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapp.data.Database
import com.example.myapp.databinding.ListExercisesBinding
import com.example.myapp.models.ExerciseModel
import com.example.myapp.repositories.ExerciseRepository
import com.example.myapp.adapters.ExerciseAdapter
import com.example.myapp.viewModels.ExerciseFactory
import com.example.myapp.viewModels.ExerciseViewModel


class ExerciseFragment : Fragment() {
    private lateinit var binding: ListExercisesBinding
    private lateinit var exerciseRepository: ExerciseRepository
    private lateinit var exerciseViewModel: ExerciseViewModel
    private lateinit var exerciseAdapter: ExerciseAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var group = arguments?.getString("filter")



        //Log.d("debug", group?.toString() ?: "")
        binding = ListExercisesBinding.inflate(inflater, container, false)
        
        if(group == "Все"){
            group = null
        }
        else{
            binding.textHeaderGroup.text = group
        }

        val exercisesDao = Database.getInstance((context as FragmentActivity).application).exerciseDAO
        exerciseRepository = ExerciseRepository(exercisesDao)
        val exerciseFactory = ExerciseFactory(exerciseRepository)
        exerciseViewModel = ViewModelProvider(this, exerciseFactory).get(ExerciseViewModel::class.java)
        
        exerciseViewModel.filterName.setValue("%%");
        exerciseViewModel.getExercises(group)

        initRecyclerExercises()
        displayExercises()

        binding.createNewExercise.setOnClickListener {
            val transaction  = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(com.example.myapp.R.id.content, CreationExerciseFragment())
            transaction?.addToBackStack(null)
            transaction?.commit()
        }

        binding.searchExercise.addTextChangedListener(
        object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

                Toast.makeText(context, "Test-test", Toast.LENGTH_LONG)
                Log.d("test-ets", "test")
                Log.d("test-1", s.toString())
                exerciseViewModel.filterName.
                          setValue("%" + s.toString() + "%");
                exerciseViewModel.getExercises(group)
                //initRecyclerExercises()
                displayExercises()
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
        exerciseAdapter = ExerciseAdapter(
            {categoryModel: ExerciseModel -> deleteExercise(categoryModel)},
            {categoryModel:ExerciseModel-> editExercise(categoryModel)},

        )
        binding.recyclerCategories.adapter = exerciseAdapter

    }

    private fun displayExercises(){
        exerciseViewModel.exercises.observe(viewLifecycleOwner, Observer {
            exerciseAdapter.setList(it)
            exerciseAdapter.notifyDataSetChanged()
        })
    }


    private fun deleteExercise(exerciseModel: ExerciseModel) {
        exerciseViewModel.deleteExercise(exerciseModel)
    }

    private fun editExercise(exerciseModel:ExerciseModel) {
        // todo: кажется нужно просто передавать id, хотелось бы объект
        val panelEditExercise = EditExerciseFragment()
        val parameters = Bundle()
        parameters.putString("idExercise", exerciseModel.id.toString())
        parameters.putString("nameExercise", exerciseModel.name)
        parameters.putString("typeExercise", exerciseModel.type)
        parameters.putString("imageExercise", exerciseModel.image)
        parameters.putString("muscleGroupExercise", exerciseModel.muscle_group)
        parameters.putString("external_id", exerciseModel.external_id)

        panelEditExercise.arguments = parameters

        panelEditExercise.show((context as FragmentActivity).supportFragmentManager, "editExercise")
    }

    companion object {
        @JvmStatic
        fun newInstance() = ExerciseFragment()
    }
}