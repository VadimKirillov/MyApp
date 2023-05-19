package com.example.myapp.fragments

import android.os.Bundle
import android.util.Log
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
    private lateinit var binding: FragmentEditCountTrainBinding
    private lateinit var trainViewModel: TrainingViewModel
    private var idExercise:Int = 0
    private var idTrain:Int = 0
    private var idTrainLine:Int = 0
    private var nameTraining:String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentEditCountTrainBinding.inflate(inflater, container, false)
        nameTraining = arguments?.getString("nameTraining")!!
        idExercise = arguments?.getInt("idExercise")!!.toInt()
        idTrain = arguments?.getInt("idTraining")!!.toInt()
        idTrainLine = arguments?.getInt("idTrainLine")!!.toInt()

        binding.textExerciseCountName.setText(arguments?.getString("nameExercise").toString())
        binding.textCount.setText(arguments?.getString("count").toString() )

        val productDao = Database.getInstance((context as FragmentActivity).application).trainingDAO
        val trainRepository = TrainingRepository(productDao)
        val factory = TrainingFactory(trainRepository)
        trainViewModel = ViewModelProvider(this, factory).get(TrainingViewModel::class.java)
        binding.textCount.setOnClickListener{
            binding.textCount.setText("")
        }
        binding.buttonEditCount.setOnClickListener(this)

        return binding.root
    }

    override fun onClick(view: View) {
       Log.d("log-log", "${idTrainLine}, ${idExercise},${idTrain}")
        binding.textCount.text.toString().toIntOrNull()?.let {

                it1 -> trainViewModel.startUpdateLine(idTrainLine, idExercise, idTrain, it1, 30)
        }
            dismiss()
            val fragment = TrainCreatorFragment()
            val arguments = Bundle()

            arguments.putInt("idTraining", idTrain)
            arguments.putString("nameTraining", nameTraining)
            fragment.arguments = arguments
            //(context as FragmentActivity).supportFragmentManager.beginTransaction().replace(R.id.content, fragment).commit()
    }

}