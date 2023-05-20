package com.example.myapp.adapters

import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.size
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.databinding.AllTrainItemBinding
import com.example.myapp.databinding.ExerciseItemPostBinding
import com.example.myapp.databinding.TrainItemBinding
import com.example.myapp.models.LineWithExercises
import com.example.myapp.models.TrainingModel
import com.example.myapp.viewModels.TrainingViewModel
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.util.*


class ExercisePostAdapter(
                      private val trainingViewModel: TrainingViewModel) : RecyclerView.Adapter<ExercisePostAdapter.TrainingHolder>() {

    //public val trainingList = ArrayList<LineWithExercises>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingHolder {
        val binding : ExerciseItemPostBinding = ExerciseItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrainingHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: TrainingHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    fun setList(training: List<LineWithExercises>) {
        differ.submitList(training)
    }

    class TrainingHolder(val binding: ExerciseItemPostBinding) : RecyclerView.ViewHolder(binding.root) {

        private var onItemClickListener: ((String) -> Unit)? = null

        fun setOnItemClickListener(listener: (String) -> Unit) {
            onItemClickListener = listener
        }

        fun bind(
            trainingModel: LineWithExercises,


        ) {
            binding.root.setOnClickListener {
                onItemClickListener?.let {
                    it(trainingModel.toString())
                }
            }
            fun decodeBase64AndSetImage(completeImageData: String, imageView: ImageView) {
                val imageDataBytes = completeImageData.substring(completeImageData.indexOf(",") + 1)
                val stream: InputStream =
                    ByteArrayInputStream(Base64.decode(imageDataBytes.toByteArray(), Base64.DEFAULT))
                val bitmap = BitmapFactory.decodeStream(stream)
                imageView.setImageBitmap(bitmap)
            }

            binding.idTrain.text = trainingModel.exercise.id.toString()
            binding.nameExercise.text = trainingModel.exercise.name.toString()
            binding.count.text = trainingModel.playlist.count.toString()
            decodeBase64AndSetImage(trainingModel.exercise.image, binding.imageCardExercise)
            //binding.categoryExercise.text = exercisesModel.category


        }
    }

    private val differCallBack  = object : DiffUtil.ItemCallback<LineWithExercises>()
    {

        override fun areItemsTheSame(oldItem: LineWithExercises, newItem: LineWithExercises): Boolean {
            return  oldItem.playlist.id == newItem.playlist.id
        }

        override fun areContentsTheSame(oldItem: LineWithExercises, newItem: LineWithExercises): Boolean {
            return  oldItem.playlist.id == newItem.playlist.id
        }


    }

    val differ = AsyncListDiffer(this, differCallBack)
    var saveList : List<LineWithExercises>? = null




}


