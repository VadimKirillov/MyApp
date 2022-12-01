package com.example.myapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapp.R
import com.example.myapp.databinding.FragmentStartTrainBinding


class StartTrainFragment : Fragment() {
    private lateinit var binding: FragmentStartTrainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartTrainBinding.inflate(inflater,container, false )

        binding.startButton.setOnClickListener(){
            val transaction  = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.content, WaitingFragment())
            transaction?.commit()

        }

        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance() = StartTrainFragment()
    }


}