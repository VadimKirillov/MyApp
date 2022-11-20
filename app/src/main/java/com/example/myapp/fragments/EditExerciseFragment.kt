package com.example.myapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.myapp.R
import com.example.myapp.data.Database
import com.example.myapp.databinding.PanelEditExerciseBinding
import com.example.myapp.repositories.ExerciseRepository
import com.example.myapp.viewModels.ExerciseFactory
import com.example.myapp.viewModels.ExerciseViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class EditExerciseFragment : DialogFragment(), View.OnClickListener {

    private var binding: PanelEditExerciseBinding? = null
    private var exerciseRepository: ExerciseRepository? = null
    private var exerciseViewModel: ExerciseViewModel? = null
    private var factory: ExerciseFactory? = null
    private var idExercise:Int? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = PanelEditExerciseBinding.inflate(inflater, container, false)

        idExercise = arguments?.getString("idExercise")?.toInt()

        binding?.textExerciseName?.setText(arguments?.getString("nameExercise").toString())

        /*
        binding?.editCategoryExercise?.setText(arguments?.getString("categoryProduct").toString())
        binding?.editPriceExercise?.setText(arguments?.getString("priceProduct").toString())
        */

        val productDao = Database.getInstance((context as FragmentActivity).application).exerciseDAO
        exerciseRepository = ExerciseRepository(productDao)
        factory = ExerciseFactory(exerciseRepository!!)
        exerciseViewModel = ViewModelProvider(this, factory!!).get(ExerciseViewModel::class.java)


        binding?.buttonEditExercise?.setOnClickListener(this)

        return binding?.root

    }

    override fun onClick(view: View) {
        exerciseViewModel?.startUpdateExercise(idExercise.toString().toInt(), binding?.textExerciseName?.text.toString(),
            arguments?.getString("muscleGroupExercise").toString(),
            arguments?.getString("typeExercise").toString(),
            arguments?.getString("typeExercise").toString())

        dismiss()

        (context as FragmentActivity).supportFragmentManager.beginTransaction().replace(R.id.content, ExerciseFragment()).commit()
    }

}