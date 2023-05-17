package com.example.myapp.adapters

import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.databinding.PickExerciseToTrainItemBinding
import com.example.myapp.models.ExerciseModel
import com.example.myapp.viewModels.ExerciseFactory
import com.example.myapp.viewModels.ExerciseViewModel
import java.io.ByteArrayInputStream
import java.io.InputStream

class PickExerciseToTrainAdapter(private val pickExercise:(ExerciseModel, Boolean)->Unit): RecyclerView.Adapter<PickExerciseToTrainAdapter.PickExerciseToTrainHolder>() {

    private val exercisesList = ArrayList<ExerciseModel>()
    private val pickedExercisesList = MutableLiveData<List<ExerciseModel>>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PickExerciseToTrainHolder {

        val binding : PickExerciseToTrainItemBinding = PickExerciseToTrainItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return PickExerciseToTrainHolder(binding)
    }

    override fun getItemCount(): Int {
        return exercisesList.size
    }

    override fun onBindViewHolder(holder: PickExerciseToTrainAdapter.PickExerciseToTrainHolder, position: Int) {
        holder.bind(exercisesList[position], pickExercise)
    }

    fun setList(exercises: List<ExerciseModel>) {
        exercisesList.clear()
        exercisesList.addAll(exercises)
    }


    class PickExerciseToTrainHolder(val binding: PickExerciseToTrainItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            exercisesModel: ExerciseModel,
            pickExercise: (ExerciseModel, Boolean) -> Unit,
        ) {
            fun decodeBase64AndSetImage(completeImageData: String, imageView: ImageView) {
                val imageDataBytes = completeImageData.substring(completeImageData.indexOf(",") + 1)
                val stream: InputStream =
                    ByteArrayInputStream(Base64.decode(imageDataBytes.toByteArray(), Base64.DEFAULT))
                val bitmap = BitmapFactory.decodeStream(stream)
                imageView.setImageBitmap(bitmap)
            }
            // todo: заменить просто на передачу объекта
            binding.nameExercise.text = exercisesModel.name
            decodeBase64AndSetImage(exercisesModel.image, binding.imageCardExercise)
            //todo: брал состояние из массива
            binding.checkBox.isChecked = false
            //binding.categoryExercise.text = exercisesModel.category

            binding.checkBox.setOnClickListener(View.OnClickListener {
                Log.d("debug-debug", binding.checkBox.isChecked.toString())
                pickExercise(exercisesModel, binding.checkBox.isChecked)
            })

        }


    }



}