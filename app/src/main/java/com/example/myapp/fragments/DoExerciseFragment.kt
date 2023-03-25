package com.example.myapp.fragments

import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListAdapter
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.myapp.R
import com.example.myapp.data.Database
import com.example.myapp.databinding.FragmentDoExerciseBinding

import com.example.myapp.models.ExerciseModel
import com.example.myapp.models.LineWithExercises
import com.example.myapp.models.TrainingWithExercises
import com.example.myapp.repositories.TrainingRepository
import com.example.myapp.tabs.TrainingAdapter
import com.example.myapp.utils.FragmentManager
import com.example.myapp.utils.MainViewModel
import com.example.myapp.viewModels.TrainingFactory
import com.example.myapp.viewModels.TrainingViewModel
import pl.droidsonroids.gif.GifDrawable
import java.io.ByteArrayInputStream
import java.io.InputStream


class DoExerciseFragment : Fragment() {
    private var timer: CountDownTimer? = null
    private lateinit var binding: FragmentDoExerciseBinding
    private var exerciseCounter = 0

    private var ab: ActionBar? = null
    private var currentDay = 0
    //private val model: MainViewModel by activityViewModels()
    private var exList: List<LineWithExercises>? = null
    private var exListTrain: List<TrainingWithExercises>? = null

    private var trainingRepository: TrainingRepository? = null
    private var trainingViewModel: TrainingViewModel? = null
    private var trainingFactory: TrainingFactory? = null
    private var trainingAdapter: TrainingAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDoExerciseBinding.inflate(inflater, container, false)

        val trainingsDao = Database.getInstance((context as FragmentActivity).application).trainingDAO
        trainingRepository = TrainingRepository(trainingsDao)
        trainingFactory = TrainingFactory(trainingRepository!!)
        trainingViewModel = ViewModelProvider(this, trainingFactory!!).get(TrainingViewModel::class.java)


        //trainingAdapter = ArrayAdapter(trainingViewModel)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val model = trainingRepository

        //exerciseCounter = model.getExerciseCount()
        ab = (activity as AppCompatActivity).supportActionBar
        model!!.trainings.observe(viewLifecycleOwner){
            var t = it!![0].lines
            exList = t
            nextExercise()
        }
        binding.bNext.setOnClickListener {
            nextExercise()
        }
    }

    private fun nextExercise(){
        if(exerciseCounter < exList?.size!!){
            val ex = exList?.get(exerciseCounter++) ?: return
            showExercise(ex)
            //setExerciseType(ex)
            showNextExercise()
        } else {
            exerciseCounter++
            FragmentManager.setFragment(DayFinishFragment.newInstance(),
                activity as AppCompatActivity)
        }
    }

    private fun showNextExercise() = with(binding){
        if(exerciseCounter < exList?.size!!){
            val ex = exList?.get(exerciseCounter) ?: return
            tvNextName.text = ex.exercise.name
            decodeBase64AndSetImage(ex.exercise.image,imNext)
            //setTimeType(ex)
        } else {
            imNext.setImageDrawable(GifDrawable(root.context.assets, "congrats-congratulations.gif"))
            tvNextName.text = "Конец"
            //tvNextName.text = exList!![0].exercise.name
        }
    }

    private fun decodeBase64AndSetImage(completeImageData: String, imageView: ImageView) {
        // Incase you're storing into aws or other places where we have extension stored in the starting.
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
        //val title = "$exerciseCounter / ${exList?.size}"
        //ab?.title = title

    }



    companion object {
        @JvmStatic
        fun newInstance() = DoExerciseFragment()
    }




}