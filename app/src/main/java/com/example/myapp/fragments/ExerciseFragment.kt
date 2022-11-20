package com.example.myapp.fragments

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapp.data.Database
import com.example.myapp.databinding.TabExercisesBinding
import com.example.myapp.models.ExerciseModel
import com.example.myapp.repositories.ExerciseRepository
import com.example.myapp.tabs.ExerciseAdapter
import com.example.myapp.utils.FragmentManager.setFragment
import com.example.myapp.viewModels.ExerciseFactory
import com.example.myapp.viewModels.ExerciseViewModel


class ExerciseFragment : Fragment() {
    private lateinit var binding: TabExercisesBinding

    private var exerciseRepository: ExerciseRepository? = null
    private var exerciseViewModel: ExerciseViewModel? = null
    private var exerciseFactory: ExerciseFactory? = null
    private var exerciseAdapter: ExerciseAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TabExercisesBinding.inflate(inflater,container, false )

        val exercisesDao = Database.getInstance((context as FragmentActivity).application).exerciseDAO
        exerciseRepository = ExerciseRepository(exercisesDao)
        exerciseFactory = ExerciseFactory(exerciseRepository!!)
        exerciseViewModel = ViewModelProvider(this, exerciseFactory!!).get(ExerciseViewModel::class.java)

        initRecyclerExercises()
        displayExercises()

        binding.createNewExercise.setOnClickListener {
            //val nextFrag = CreationExerciseFragment()

            val transaction  = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(com.example.myapp.R.id.content, CreationExerciseFragment())
            transaction?.commit()
            //(context as FragmentActivity).supportFragmentManager.beginTransaction().replace(com.example.myapp.R.id.content,
            //    CreationExerciseFragment()).commit()
            //getActivity()?.onBackPressed()
            //binding.recyclerCategories.setVisibility(View.GONE);
            //binding.createNewExercise.setVisibility(View.GONE);
        }
        return binding.root
    }

    private fun initRecyclerExercises(){
        binding?.recyclerCategories?.layoutManager = LinearLayoutManager(context)
        exerciseAdapter = ExerciseAdapter({categoryModel: ExerciseModel -> deleteExercise(categoryModel)},
            {categoryModel:ExerciseModel-> editExercise(categoryModel)})
        binding?.recyclerCategories?.adapter = exerciseAdapter

    }


    private fun displayExercises(){
        exerciseViewModel?.exercises?.observe(viewLifecycleOwner, Observer {
            exerciseAdapter?.setList(it)
            exerciseAdapter?.notifyDataSetChanged()
        })
    }



    private fun deleteExercise(exerciseModel: ExerciseModel) {
        exerciseViewModel?.deleteExercise(exerciseModel)
    }

    private fun editExercise(exerciseModel:ExerciseModel) {
        val panelEditExercise = EditExerciseFragment()
        val parameters = Bundle()
        parameters.putString("idExercise", exerciseModel.id.toString())
        parameters.putString("nameExercise", exerciseModel.name)
        parameters.putString("typeExercise", exerciseModel.type)
        parameters.putString("muscleGroupExercise", exerciseModel.muscle_group)
        panelEditExercise.arguments = parameters

        panelEditExercise.show((context as FragmentActivity).supportFragmentManager, "editExercise")
    }



    companion object {

        @JvmStatic
        fun newInstance() = ExerciseFragment()
    }
}