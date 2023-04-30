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
import com.example.myapp.adapters.PickTrainToStartAdapter
import com.example.myapp.adapters.TrainingsAdapter
import com.example.myapp.data.Database
import com.example.myapp.data.GroupExercise
import com.example.myapp.databinding.FragmentAllTrainingsBinding
import com.example.myapp.databinding.FragmentPickTrainToStartBinding
import com.example.myapp.fragments.ExerciseFragment
import com.example.myapp.fragments.TrainCreatorFragment
import com.example.myapp.fragments.WaitingFragment
import com.example.myapp.models.TrainingModel
import com.example.myapp.repositories.TrainingRepository
import com.example.myapp.viewModels.TrainingFactory
import com.example.myapp.viewModels.TrainingViewModel


class PickTrainToStartFragment : Fragment(), PickTrainToStartAdapter.Listener{

        private lateinit var binding: FragmentPickTrainToStartBinding
        private lateinit var trainingViewModel: TrainingViewModel
        private lateinit var trainingFactory: TrainingFactory
        private lateinit var pickTrainAdapter: PickTrainToStartAdapter

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            binding = FragmentPickTrainToStartBinding.inflate(inflater,container, false )

            val trainingsDao = Database.getInstance((context as FragmentActivity).application).trainingDAO
            val trainingRepository = TrainingRepository(trainingsDao)
            trainingFactory = TrainingFactory(trainingRepository)
            trainingViewModel = ViewModelProvider(this, trainingFactory).get(TrainingViewModel::class.java)
            initRecyclerTrainings()
            displayTrainings()

            return binding.root
        }

        private fun initRecyclerTrainings(){
            binding.recyclerTrainingsToStart.layoutManager = LinearLayoutManager(context)
            // todo: кажется адаптер должен вызывать только методы view model, но не факт
            pickTrainAdapter = PickTrainToStartAdapter(this)
            binding.recyclerTrainingsToStart.adapter = pickTrainAdapter
        }

        private fun displayTrainings(){
            trainingViewModel.getAllTrainings()
            trainingViewModel.nameTraining.setValue("gsdfdsf")
            trainingViewModel.allTrainings.observe(viewLifecycleOwner, Observer {
                pickTrainAdapter.setList(it)
                pickTrainAdapter.notifyDataSetChanged()

            })

        }
        override fun onClick(trainingModel: TrainingModel) {
            super.onClick(trainingModel)
            //Toast.makeText(context, trainingModel.name, Toast.LENGTH_LONG).show()

            val transaction  = activity?.supportFragmentManager?.beginTransaction()
            val parameters = Bundle()
            parameters.putString("idTraining", trainingModel.id.toString())
            val fragment = WaitingFragment()
            fragment.arguments = parameters
            transaction?.replace(R.id.content, fragment)
            transaction?.addToBackStack(null)
            transaction?.commit()
        }





        companion object {
            @JvmStatic
            fun newInstance() = PickTrainToStartFragment()
        }
    }