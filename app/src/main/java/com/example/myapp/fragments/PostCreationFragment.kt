package com.example.myapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.apollographql.apollo3.api.Optional
import com.example.CreateTrainingsMutation
import com.example.myapp.R
import com.example.myapp.data.Database
import com.example.myapp.data.model.UtilClient
import com.example.myapp.databinding.FragmentPostCreationBinding
import com.example.myapp.databinding.FragmentTrainCreationBinding
import com.example.myapp.models.TrainingModel
import com.example.myapp.models.TrainingWithExercises
import com.example.myapp.repositories.TrainingRepository
import com.example.myapp.viewModels.TrainingFactory
import com.example.myapp.viewModels.TrainingViewModel
import com.example.type.TrainingInput
import com.example.type.TrainingLineInput
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class PostCreationFragment : DialogFragment(), View.OnClickListener {
    private lateinit var binding: FragmentPostCreationBinding
    private var trainingViewModel: TrainingViewModel? = null
    private var trainingFactory: TrainingFactory? = null
    private lateinit var training: TrainingWithExercises


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentPostCreationBinding.inflate(inflater, container, false)
        val id = arguments?.getInt("idTraining")!!
         Log.e("LivedataTrainingId", "${id}")
        val trainingDao = Database.getInstance((context as FragmentActivity).application).trainingDAO
        val trainRepository = TrainingRepository(trainingDao)
        training = trainRepository.getTrainingWithExercisesByIdList(id).get(0)



        //trainingFactory = TrainingFactory(trainingRepository!!)
        //trainingViewModel = ViewModelProvider(this, trainingFactory!!).get(TrainingViewModel::class.java)

        binding.buttonCreatePost.setOnClickListener {

            val client = UtilClient.instance

            GlobalScope.launch{

                val listTrainingLines = mutableListOf<TrainingLineInput>()
                for (line in training.lines){
                    listTrainingLines.add(TrainingLineInput(
                    exercise = Optional.present(line.exercise.external_id),
                     count = Optional.present(line.playlist.count)
                     ))
                }

                val response2 = client.apolloClient.mutation(CreateTrainingsMutation(
                    TrainingInput(
                            name = Optional.present(binding.textTrainName.text.toString()),
                            text = Optional.present(binding.editTextDescription.text.toString()),
                            training_lines = Optional.present(
                                listTrainingLines
                            ),
                        )
                    )
                ).execute()
                Log.d("query", "Sent post")
            }

            training.lines
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