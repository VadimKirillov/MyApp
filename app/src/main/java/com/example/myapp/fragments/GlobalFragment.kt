package com.example.myapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapp.PickTrainToStartFragment
import com.example.myapp.R
import com.example.myapp.databinding.FragmentGlobalBinding
import com.example.myapp.databinding.FragmentStartTrainBinding


class GlobalFragment: Fragment() {
    private lateinit var binding: FragmentGlobalBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        
        binding = FragmentGlobalBinding.inflate(inflater,container, false)
        
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance() = GlobalFragment()
    }


}