package com.example.myapp.tabs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.databinding.ExerciseItemBinding
import com.example.myapp.models.ExerciseModel

class ExerciseAdapter(private val deleteExercise:(ExerciseModel)->Unit,
                      private val editExercise:(ExerciseModel)->Unit,
                      private val pickExercise:(ExerciseModel)->Unit,) : RecyclerView.Adapter<ExerciseAdapter.ExerciseHolder>() {

    private val exercisesList = ArrayList<ExerciseModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseHolder {

        val binding : ExerciseItemBinding = ExerciseItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExerciseHolder(binding)
    }

    override fun getItemCount(): Int {
        return exercisesList.size
    }

    override fun onBindViewHolder(holder: ExerciseHolder, position: Int) {
        holder.bind(exercisesList[position], deleteExercise, editExercise, pickExercise)
    }

    fun setList(exercises: List<ExerciseModel>) {
        exercisesList.clear()
        exercisesList.addAll(exercises)

    }


    class ExerciseHolder(val binding: ExerciseItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            exercisesModel: ExerciseModel,
            deleteExercise: (ExerciseModel) -> Unit,
            editExercise: (ExerciseModel) -> Unit,
            pickExercise: (ExerciseModel) -> Unit,

        ) {

            binding.idExercise.text = exercisesModel.id.toString()
            binding.nameExercise.text = exercisesModel.name
            //binding.categoryExercise.text = exercisesModel.category


            binding.editExercise.setOnClickListener(View.OnClickListener {
                editExercise(exercisesModel)
            })

            binding.deleteExercise.setOnClickListener(View.OnClickListener {
                deleteExercise(exercisesModel)
            })

            binding.pickExercise.setOnClickListener(View.OnClickListener {
                deleteExercise(exercisesModel)
            })

        }


    }

}