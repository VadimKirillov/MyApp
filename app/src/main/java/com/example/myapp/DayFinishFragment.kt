package com.example.myapp

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp.databinding.FragmentDayFinishBinding
import com.example.myapp.fragments.COUNT_DOWN_TIME
import com.example.myapp.fragments.OptionsFragment
import com.example.myapp.utils.FragmentManager
import com.example.myapp.utils.TimeUtils
import pl.droidsonroids.gif.GifDrawable

const val CONGRATS_TIME = 1000L
class DayFinishFragment : Fragment() {
    private lateinit var binding: FragmentDayFinishBinding
    private var ab: ActionBar? = null
    private lateinit var timer: CountDownTimer
    var soundCongratulation: MediaPlayer? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDayFinishBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ab = (activity as AppCompatActivity).supportActionBar
        //ab?.title = getString(R.string.done)
        binding.imMain.setImageDrawable(GifDrawable((activity as AppCompatActivity).assets,
           "congrats-congratulations.gif"))
        binding.bDone.setOnClickListener {
            FragmentManager.setFragment(StartTrainFragment.newInstance(),
                activity as AppCompatActivity
            )
        }
        soundCongratulation = MediaPlayer.create(context, R.raw.finish_sound)
        startTimer()
    }
    private fun startTimer() = with(binding){
        timer = object : CountDownTimer(CONGRATS_TIME, 1){
            override fun onTick(restTime: Long) {

            }

            override fun onFinish() {
                soundCongratulation?.start()

            }

        }.start()
    }


    companion object {
        @JvmStatic
        fun newInstance() = DayFinishFragment()
    }
}