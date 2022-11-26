package com.example.myapp.fragments

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp.DayFinishFragment
import com.example.myapp.R


import com.example.myapp.databinding.FragmentWaitingBinding
import com.example.myapp.fragments.ExerciseFragment
import com.example.myapp.fragments.OptionsFragment
import com.example.myapp.utils.FragmentManager
import com.example.myapp.utils.TimeUtils
import com.example.myapp.utils.TimeUtils.getTime

const val COUNT_DOWN_TIME = 5000L
class WaitingFragment : Fragment() {
    private var ab: ActionBar? = null
    var soundEnd: MediaPlayer? = null
    var soundWarning: MediaPlayer? = null
    var onceTickWarning:Boolean = false
    var onceTickLastWarning:Boolean = false
    private lateinit var binding: FragmentWaitingBinding
    private lateinit var timer: CountDownTimer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWaitingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ab = (activity as AppCompatActivity).supportActionBar
        //ab?.title = getString(R.string.waiting)
        soundEnd = MediaPlayer.create(context, R.raw.korotkiy)
        soundWarning = MediaPlayer.create(context, R.raw.nizkiy)
        binding.pBar.max = COUNT_DOWN_TIME.toInt()
        startTimer()
    }

    private fun startTimer() = with(binding){
        timer = object : CountDownTimer(COUNT_DOWN_TIME, 1){
            override fun onTick(restTime: Long) {
                tvTimer.text = TimeUtils.getTime(restTime)
                pBar.progress = restTime.toInt()
                if (TimeUtils.getTime(restTime) == "00:01" && !onceTickWarning){
                    soundWarning?.start()
                    onceTickWarning = true
                }
                if (TimeUtils.getTime(restTime) == "00:00" && !onceTickLastWarning){
                    soundWarning?.start()
                    onceTickLastWarning = true
                }
            }

            override fun onFinish() {
                soundEnd?.start()
                FragmentManager.setFragment(
                    DayFinishFragment.newInstance(),
                    activity as AppCompatActivity
                )
            }

        }.start()
    }

    override fun onDetach() {
        super.onDetach()
        timer.cancel()
    }

    companion object {
        @JvmStatic
        fun newInstance() = WaitingFragment()
    }
}