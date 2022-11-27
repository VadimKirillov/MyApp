package com.example.myapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.myapp.R


class EditCountTrainFragment : DialogFragment(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_edit_count_train, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditCountTrainFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }
}