package com.example.myapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.databinding.AllTrainItemBinding
import com.example.myapp.models.TrainingModel


class TrainingsAdapter(private val deleteTraining:(TrainingModel)->Unit,
                       private val editTraining:(TrainingModel)->Unit) : RecyclerView.Adapter<TrainingsAdapter.TrainingsHolder>() {

    private val trainingList = ArrayList<TrainingModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingsHolder {
        val binding : AllTrainItemBinding = AllTrainItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrainingsHolder(binding)
    }

    override fun getItemCount(): Int {
        return trainingList.size
    }

    override fun onBindViewHolder(holder: TrainingsHolder, position: Int) {
        holder.bind(trainingList[position], deleteTraining, editTraining)
    }

    fun setList(trainings: List<TrainingModel>) {
        trainingList.clear()
        trainingList.addAll(trainings)
    }


    class TrainingsHolder(val binding: AllTrainItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            trainingModel: TrainingModel,
            deleteTraining: (TrainingModel) -> Unit,
            editTraining: (TrainingModel) -> Unit

        ) {

            binding.idTrain.text = trainingModel.id.toString()
            binding.name.text = trainingModel.name.toString()

            binding.editExercise.setOnClickListener(View.OnClickListener {
                editTraining(trainingModel)
            })

            binding.deleteExercise.setOnClickListener(View.OnClickListener {
                deleteTraining(trainingModel)
            })
        }
    }
}