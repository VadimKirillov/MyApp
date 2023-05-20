package com.example.myapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.myapp.R
import com.example.myapp.data.Database
import com.example.myapp.databinding.FragmentPostCreationBinding
import com.example.myapp.databinding.FragmentTrainCreationBinding
import com.example.myapp.models.TrainingModel
import com.example.myapp.repositories.TrainingRepository
import com.example.myapp.viewModels.TrainingFactory
import com.example.myapp.viewModels.TrainingViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class PostCreationFragment : DialogFragment(), View.OnClickListener {
    private lateinit var binding: FragmentPostCreationBinding
    private var trainingRepository: TrainingRepository? = null
    private var trainingViewModel: TrainingViewModel? = null
    private var trainingFactory: TrainingFactory? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentPostCreationBinding.inflate(inflater, container, false)

        val trainingDao = Database.getInstance((context as FragmentActivity).application).trainingDAO
        trainingRepository = TrainingRepository(trainingDao)
        trainingFactory = TrainingFactory(trainingRepository!!)
        trainingViewModel = ViewModelProvider(this, trainingFactory!!).get(TrainingViewModel::class.java)

        binding.buttonCreatePost.setOnClickListener {
            val trm = TrainingModel(0,binding.textTrainName.text.toString())
            GlobalScope.launch {
                trainingDao.insertTraining(trm)
            }
            dismiss()
            //(context as FragmentActivity).supportFragmentManager.beginTransaction().replace(R.id.content, AllTrainingsFragment()).commit()
        }

        //binding.buttonCreateTrain.setOnClickListener(this)
        return binding.root
    }

    override fun onClick(view: View) {

        dismiss()
        //(context as FragmentActivity).supportFragmentManager.beginTransaction().replace(R.id.content, AllTrainingsFragment()).commit()
    }

}