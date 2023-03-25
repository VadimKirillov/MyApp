package com.example.myapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.databinding.TrainItemBinding
import com.example.myapp.models.LineWithExercises


class TrainingAdapter(private val deleteTraining:(LineWithExercises)->Unit,
                      private val editTraining:(LineWithExercises)->Unit) : RecyclerView.Adapter<TrainingAdapter.TrainingHolder>() {

    private val trainingList = ArrayList<LineWithExercises>()

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

    fun setList(training: List<LineWithExercises>) {
        trainingList.clear()
        trainingList.addAll(training)
    }


    class TrainingHolder(val binding: TrainItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            trainingModel: LineWithExercises,
            deleteTraining: (LineWithExercises) -> Unit,
            editTraining: (LineWithExercises) -> Unit

        ) {

            binding.idTrain.text = trainingModel.exercise.id.toString()
            binding.nameExercise.text = trainingModel.exercise.name.toString()
            binding.count.text = trainingModel.playlist.count.toString()
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