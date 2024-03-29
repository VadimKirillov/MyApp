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
import com.example.myapp.ui.login.LoginActivity

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
        binding.profileButton.setOnClickListener(){
            val transaction  = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.content, ProfileFragment())
            transaction?.addToBackStack(null)
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
//            getActivity()?.finish();
//            exitProcess(0);
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
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