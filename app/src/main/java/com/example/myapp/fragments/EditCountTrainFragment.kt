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
import com.example.myapp.databinding.FragmentEditCountTrainBinding
import com.example.myapp.databinding.PanelEditExerciseBinding
import com.example.myapp.repositories.ExerciseRepository
import com.example.myapp.repositories.TrainingRepository
import com.example.myapp.viewModels.ExerciseFactory
import com.example.myapp.viewModels.ExerciseViewModel
import com.example.myapp.viewModels.TrainingFactory
import com.example.myapp.viewModels.TrainingViewModel


class EditCountTrainFragment : DialogFragment(), View.OnClickListener {

    private var binding: FragmentEditCountTrainBinding? = null
    private var trainRepository: TrainingRepository? = null
    private var trainViewModel: TrainingViewModel? = null
    private var factory:  TrainingFactory? = null
    private var idExercise:Int? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentEditCountTrainBinding.inflate(inflater, container, false)

        idExercise = arguments?.getString("idExercise")?.toInt()

        binding?.textExerciseCountName?.setText(arguments?.getString("nameExercise").toString())
        binding?.textCount?.setText(arguments?.getString("count").toString() )

        val productDao = Database.getInstance((context as FragmentActivity).application).trainingDAO
        trainRepository = TrainingRepository(productDao)
        factory = TrainingFactory(trainRepository!!)
        trainViewModel = ViewModelProvider(this, factory!!).get(TrainingViewModel::class.java)


        binding?.buttonEditCount?.setOnClickListener(this)

        return binding?.root

    }

    override fun onClick(view: View) {

        idExercise?.let { binding?.textCount?.text.toString().toIntOrNull()
            ?.let { it1 -> trainViewModel?.startUpdateLine(1, it, it1) } }
        dismiss()
        (context as FragmentActivity).supportFragmentManager.beginTransaction().replace(R.id.content, TrainCreatorFragment()).commit()
    }

}