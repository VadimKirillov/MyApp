package com.example.myapp.adapters

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.databinding.ExerciseItemBinding
import com.example.myapp.databinding.ExerciseItemGlobalBinding
import com.example.myapp.models.ExerciseModel
import java.io.ByteArrayInputStream
import java.io.InputStream


class DiffUtilCallBack : DiffUtil.ItemCallback<ExerciseModel>() {
    override fun areItemsTheSame(oldItem: ExerciseModel, newItem: ExerciseModel): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: ExerciseModel, newItem: ExerciseModel): Boolean {
        return oldItem.name == newItem.name
    }

}

class ExerciseAdapter(
    private val deleteExercise: (ExerciseModel) -> Unit,
    private val editExercise: (ExerciseModel) -> Unit,
    private val uploadExercise: (ExerciseModel) -> Unit,

) : PagedListAdapter<ExerciseModel, ExerciseAdapter.ExerciseHolder>(DiffUtilCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseHolder {

        val binding : ExerciseItemBinding = ExerciseItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ExerciseHolder(binding)
    }

    override fun onBindViewHolder(holder: ExerciseHolder, position: Int) {
        if(position <= -1 || getItem(position) == null){
            return
        }
        holder.bind(getItem(position)!!, deleteExercise, editExercise, uploadExercise)
    }



    class ExerciseHolder(val binding: ExerciseItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            exercisesModel: ExerciseModel,
            deleteExercise: (ExerciseModel) -> Unit,
            editExercise: (ExerciseModel) -> Unit,
            uploadExercise: (ExerciseModel) -> Unit,
        ) {
            fun decodeBase64AndSetImage(completeImageData: String, imageView: ImageView) {
                val imageDataBytes = completeImageData.substring(completeImageData.indexOf(",") + 1)
                val stream: InputStream =
                    ByteArrayInputStream(Base64.decode(imageDataBytes.toByteArray(), Base64.DEFAULT))
                val bitmap = BitmapFactory.decodeStream(stream)
                imageView.setImageBitmap(bitmap)
            }
            // todo: заменить просто на передачу объекта
            binding.idExercise.text = exercisesModel.id.toString()
            binding.nameExercise.text = exercisesModel.name
            binding.groupExercise.text = exercisesModel.muscle_group
            decodeBase64AndSetImage(exercisesModel.image, binding.imageCardExercise)

            //binding.categoryExercise.text = exercisesModel.category


            binding.editExercise.setOnClickListener(View.OnClickListener {
                editExercise(exercisesModel)
            })

            binding.deleteExercise.setOnClickListener(View.OnClickListener {
                deleteExercise(exercisesModel)
            })

            binding.uploadExercise.setOnClickListener(View.OnClickListener{
                uploadExercise(exercisesModel)
            })

        }

    }

}


