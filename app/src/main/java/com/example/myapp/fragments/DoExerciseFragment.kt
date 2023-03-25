package com.example.myapp.fragments

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.example.myapp.R
import com.example.myapp.data.Database
import com.example.myapp.databinding.FragmentDoExerciseBinding

import com.example.myapp.models.LineWithExercises
import com.example.myapp.repositories.TrainingRepository
import com.example.myapp.utils.FragmentManager
import pl.droidsonroids.gif.GifDrawable
import java.io.ByteArrayInputStream
import java.io.InputStream

val CONGRATULATION_GIF = "congrats-congratulations.gif"

class DoExerciseFragment : Fragment() {
    private lateinit var binding: FragmentDoExerciseBinding
    private var exerciseCounter = 0

    private var actionBar: ActionBar? = null
    private lateinit var trainingExercisesList: List<LineWithExercises>
//    private lateinit var exListTrain: List<TrainingWithExercises> todo: может нафиг его

    private lateinit var trainingRepository: TrainingRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDoExerciseBinding.inflate(inflater, container, false)

        val trainingsDao = Database.getInstance((context as FragmentActivity).application).trainingDAO
        trainingRepository = TrainingRepository(trainingsDao)
//        trainingFactory = TrainingFactory(trainingRepository!!)
//        trainingViewModel = ViewModelProvider(this, trainingFactory).get(TrainingViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actionBar = (activity as AppCompatActivity).supportActionBar
        trainingRepository.trainings.observe(viewLifecycleOwner){
            var lines = it!![0].lines
            trainingExercisesList = lines
            nextExercise()
        }
        binding.bNext.setOnClickListener {
            nextExercise()
        }
    }

    private fun nextExercise(){
        if(exerciseCounter < trainingExercisesList.size){
            val item = trainingExercisesList.get(exerciseCounter++)
            showExercise(item)
            showNextExercise()
        } else {
            // todo: мейби тут граф
            FragmentManager.setFragment(DayFinishFragment.newInstance(),
                activity as AppCompatActivity)
        }
    }

    private fun showNextExercise() = with(binding){
        if(exerciseCounter < trainingExercisesList.size){
            val item = trainingExercisesList.get(exerciseCounter)
            tvNextName.text = item.exercise.name
            decodeBase64AndSetImage(item.exercise.image, imNext)
        } else {
            imNext.setImageDrawable(GifDrawable(root.context.assets, CONGRATULATION_GIF))
            tvNextName.text = getString(R.string.end_train)
        }
    }

    private fun decodeBase64AndSetImage(completeImageData: String, imageView: ImageView) {
        val imageDataBytes = completeImageData.substring(completeImageData.indexOf(",") + 1)
        val stream: InputStream =
            ByteArrayInputStream(Base64.decode(imageDataBytes.toByteArray(), Base64.DEFAULT))
        val bitmap = BitmapFactory.decodeStream(stream)
        imageView.setImageBitmap(bitmap)
    }

    private fun showExercise(exercise: LineWithExercises) = with(binding){
        decodeBase64AndSetImage(exercise.exercise.image, imMain)
        tvName.text = exercise.exercise.name
        val cnt = exercise.playlist.count.toString()
        tvTime.text = "$cnt раз"
    }

    companion object {
        @JvmStatic
        fun newInstance() = DoExerciseFragment()
    }

}