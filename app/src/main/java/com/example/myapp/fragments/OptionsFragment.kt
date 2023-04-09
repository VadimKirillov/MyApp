package com.example.myapp.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapp.MuscleGroupPickerFragment
import com.example.myapp.R
import com.example.myapp.databinding.FragmentOptionsBinding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlin.system.exitProcess

val URL_GIT = "https://github.com/VadimKirillov/MyApp"
val URL_LANDING = "https://staszerling2.wixsite.com/fitness"
val MAIL_ADDRESS = "vadim.kirillov2001@gmail.com"
val DEFAULT_MESSAGE = "Гачи Привет "

class OptionsFragment : Fragment() {
    private lateinit var binding: FragmentOptionsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOptionsBinding.inflate(inflater,container, false)
        // todo: в граф
        binding.aboutButton.setOnClickListener(){
            val transaction  = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.content, AboutFragment())
            transaction?.commit()
        }

        binding.gitBotton.setOnClickListener(){
            goToUrl(URL_GIT)
        }
        binding.webBotton.setOnClickListener(){
            goToUrl(URL_LANDING)
        }

        binding.emailBotton.setOnClickListener(){
            val intent = Intent(Intent.ACTION_VIEW)
            val data = Uri.parse(
                "mailto:${MAIL_ADDRESS}?" +
                        "subject=" + "${Uri.encode(getString(R.string.app_name))}" +
                        "&body=${Uri.encode(DEFAULT_MESSAGE)}"
            )
            intent.data = data
            startActivity(intent)
        }
        binding.exitButton.setOnClickListener(){
            getActivity()?.finish();
            exitProcess(0);
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