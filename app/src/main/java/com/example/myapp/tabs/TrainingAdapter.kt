package com.example.myapp.tabs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.databinding.ExerciseItemBinding
import com.example.myapp.databinding.TrainItemBinding
import com.example.myapp.models.ExerciseModel
import com.example.myapp.models.TrainingModel


class TrainingAdapter(private val deleteTraining:(TrainingModel)->Unit,
                      private val editTraining:(TrainingModel)->Unit) : RecyclerView.Adapter<TrainingAdapter.TrainingHolder>() {

    private val trainingList = ArrayList<TrainingModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingHolder {

        val binding : TrainItemBinding = TrainItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrainingHolder(binding)
    }

    override fun getItemCount(): Int {
        return trainingList.size
    }

    override fun onBindViewHolder(holder: TrainingHolder, position: Int) {
        holder.bind(trainingList[position], deleteTraining, editTraining)
    }

    fun setList(training: List<TrainingModel>) {
        trainingList.clear()
        trainingList.addAll(training)

    }


    class TrainingHolder(val binding: TrainItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            trainingModel: TrainingModel,
            deleteTraining: (TrainingModel) -> Unit,
            editTraining: (TrainingModel) -> Unit

        ) {

            binding.idTrain.text = trainingModel.id.toString()
            binding.nameExercise.text = trainingModel.name
            //binding.categoryExercise.text = exercisesModel.category


            binding.editExercise.setOnClickListener(View.OnClickListener {
                editTraining(trainingModel)
            })

            binding.deleteExercise.setOnClickListener(View.OnClickListener {
                deleteTraining(trainingModel)
            })

        }


    }

}