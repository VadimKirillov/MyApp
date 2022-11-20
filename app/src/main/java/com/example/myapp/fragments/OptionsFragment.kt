package com.example.myapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapp.R

import com.example.myapp.databinding.FragmentOptionsBinding
import com.example.myapp.databinding.TabExercisesBinding

class OptionsFragment : Fragment() {
    private lateinit var binding: FragmentOptionsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOptionsBinding.inflate(inflater,container, false )

        binding.startTimer.setOnClickListener(){
            val transaction  = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.content, WaitingFragment())
            transaction?.commit()
        }
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance() = OptionsFragment()
    }
}