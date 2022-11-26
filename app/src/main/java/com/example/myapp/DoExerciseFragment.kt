package com.example.myapp

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp.databinding.FragmentDoExerciseBinding
import com.example.myapp.models.ExerciseModel
import com.example.myapp.utils.FragmentManager
import com.example.myapp.utils.MainViewModel
import pl.droidsonroids.gif.GifDrawable


class DoExerciseFragment : Fragment() {
    private var timer: CountDownTimer? = null
    private lateinit var binding: FragmentDoExerciseBinding
    private var exerciseCounter = 0
    //private var exList: ArrayList<ExerciseModel>? = null
    private var ab: ActionBar? = null
    private var currentDay = 0
    //private val model: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDoExerciseBinding.inflate(inflater, container, false)
        return binding.root
    }


}