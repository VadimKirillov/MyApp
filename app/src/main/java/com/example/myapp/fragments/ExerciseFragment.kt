package com.example.myapp.fragments

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapp.databinding.TabExercisesBinding


class ExerciseFragment : Fragment() {
    private lateinit var binding: TabExercisesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TabExercisesBinding.inflate(inflater,container, false )

        binding.createNewExercise.setOnClickListener {
            val nextFrag = CreationExerciseFragment()

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(binding.exholder.id, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit()
            binding.recyclerCategories.setVisibility(View.GONE);
            binding.createNewExercise.setVisibility(View.GONE);
        }
        return binding.root
    }



    companion object {

        @JvmStatic
        fun newInstance() = ExerciseFragment()
    }
}