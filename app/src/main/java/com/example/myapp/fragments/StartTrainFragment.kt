package com.example.myapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapp.PickTrainToStartFragment
import com.example.myapp.R
import com.example.myapp.databinding.FragmentStartTrainBinding


class StartTrainFragment : Fragment() {
    private lateinit var binding: FragmentStartTrainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // todo: перенести в граф
        binding = FragmentStartTrainBinding.inflate(inflater,container, false)
        binding.startButton.setOnClickListener(){
            //findNavController()?.navigate(R.id.action_startTrainFragment_to_waitingFragment)
            val transaction  = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.content, PickTrainToStartFragment())
            transaction?.addToBackStack(null)
            transaction?.commit()

        }
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance() = StartTrainFragment()
    }


}