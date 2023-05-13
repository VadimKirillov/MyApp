package com.example.myapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.myapp.data.Database
import com.example.myapp.databinding.FragmentTrainCreationBinding
import com.example.myapp.fragments.AllTrainingsFragment
import com.example.myapp.models.TrainingModel
import com.example.myapp.repositories.TrainingRepository
import com.example.myapp.viewModels.TrainingFactory
import com.example.myapp.viewModels.TrainingViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class TrainCreationFragment : DialogFragment(), View.OnClickListener {
    private lateinit var binding: FragmentTrainCreationBinding
    private var trainingRepository: TrainingRepository? = null
    private var trainingViewModel: TrainingViewModel? = null
    private var trainingFactory: TrainingFactory? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentTrainCreationBinding.inflate(inflater, container, false)

        val trainingDao = Database.getInstance((context as FragmentActivity).application).trainingDAO
        trainingRepository = TrainingRepository(trainingDao)
        trainingFactory = TrainingFactory(trainingRepository!!)
        trainingViewModel = ViewModelProvider(this, trainingFactory!!).get(TrainingViewModel::class.java)

        binding.buttonCreateTrain.setOnClickListener {
            val trm = TrainingModel(0,binding.textTrainName.text.toString())
            GlobalScope.launch {
                trainingDao.insertTraining(trm)
            }
            dismiss()
            (context as FragmentActivity).supportFragmentManager.beginTransaction().replace(R.id.content, AllTrainingsFragment()).commit()
        }

        //binding.buttonCreateTrain.setOnClickListener(this)
        return binding.root
    }

    override fun onClick(view: View) {

        dismiss()
        (context as FragmentActivity).supportFragmentManager.beginTransaction().replace(R.id.content, AllTrainingsFragment()).commit()
    }

}