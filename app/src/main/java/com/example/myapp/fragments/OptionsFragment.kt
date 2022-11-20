package com.example.myapp.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapp.R
import com.example.myapp.databinding.FragmentOptionsBinding


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

        binding.buttonweb.setOnClickListener(){
            goToUrl("http://stackoverflow.com/")
        }

        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance() = OptionsFragment()
    }

    private fun goToUrl(url: String) {
        val uriUrl: Uri = Uri.parse(url)
        val launchBrowser = Intent(Intent.ACTION_VIEW, uriUrl)
        startActivity(launchBrowser)
    }

}