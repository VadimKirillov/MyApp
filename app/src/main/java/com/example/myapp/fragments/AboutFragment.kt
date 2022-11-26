package com.example.myapp.fragments

import android.R
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.fragment.app.Fragment
import com.example.myapp.databinding.AboutFragmentBinding


class AboutFragment :Fragment() {
    private lateinit var binding: AboutFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AboutFragmentBinding.inflate(inflater,container, false )
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        //val navBar = (activity as AppCompatActivity?)?.findViewById<BottomNavigationView>()
        //navBar.visibility = View.GONE

        binding.backButton.setOnClickListener {
            (activity as AppCompatActivity?)!!.supportActionBar!!.show()
            val transaction  = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.content, OptionsFragment())
            transaction?.commit()

        }
        return binding.root
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(context as Activity)
                val transaction  = activity?.supportFragmentManager?.beginTransaction()
                transaction?.replace(R.id.content, OptionsFragment())
                transaction?.commit()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        //(activity as AppCompatActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = AboutFragment()
    }


}