package com.example.myapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.databinding.ExerciseItemBinding
import com.example.myapp.models.ExerciseModel


class DiffUtilCallBack : DiffUtil.ItemCallback<ExerciseModel>() {
    override fun areItemsTheSame(oldItem: ExerciseModel, newItem: ExerciseModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ExerciseModel, newItem: ExerciseModel): Boolean {
        return oldItem.id == newItem.id
    }

}



class ExerciseAdapter(
    private val deleteExercise: (ExerciseModel) -> Unit,
    private val editExercise: (ExerciseModel) -> Unit,

) : PagedListAdapter<ExerciseModel, ExerciseAdapter.ExerciseHolder>(DiffUtilCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseHolder {

        val binding : ExerciseItemBinding = ExerciseItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ExerciseHolder(binding)
    }

    override fun onBindViewHolder(holder: ExerciseHolder, position: Int) {
        if(position <= -1 || getItem(position) == null){
            return
        }
        holder.bind(getItem(position)!!, deleteExercise, editExercise)
    }



    class ExerciseHolder(val binding: ExerciseItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            exercisesModel: ExerciseModel,
            deleteExercise: (ExerciseModel) -> Unit,
            editExercise: (ExerciseModel) -> Unit,

        ) {
            // todo: заменить просто на передачу объекта
            binding.idExercise.text = exercisesModel.id.toString()
            binding.nameExercise.text = exercisesModel.name
            //binding.categoryExercise.text = exercisesModel.category


            binding.editExercise.setOnClickListener(View.OnClickListener {
                editExercise(exercisesModel)
            })

            binding.deleteExercise.setOnClickListener(View.OnClickListener {
                deleteExercise(exercisesModel)
            })

        }


    }

}