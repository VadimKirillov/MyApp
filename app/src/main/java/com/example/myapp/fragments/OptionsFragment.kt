package com.example.myapp.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.myapp.MuscleGroupPickerFragment
import com.example.myapp.R
import com.example.myapp.databinding.FragmentOptionsBinding


class OptionsFragment : Fragment() {
    private lateinit var binding: FragmentOptionsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOptionsBinding.inflate(inflater,container, false )
        //if(arguments?.getString("Muscle") != null)
        //    binding.exitButton.setText(arguments?.getString("Muscle").toString())
        binding.aboutButton.setOnClickListener(){
            val transaction  = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.content, AboutFragment())
            transaction?.commit()
        }
        binding.gitBotton.setOnClickListener(){
            goToUrl("https://github.com/VadimKirillov/MyApp")
        }
        binding.webBotton.setOnClickListener(){

            goToUrl("https://staszerling2.wixsite.com/fitness")
        }

        binding.emailBotton.setOnClickListener(){
            val intent = Intent(Intent.ACTION_VIEW)
            val data = Uri.parse(
                "mailto:vadim.kirillov2001@gmail.com?subject=" + Uri.encode("Fitness App") + "&body=" + Uri.encode("Гачи Привет ")
            )
            intent.data = data
            startActivity(intent)
        }
        binding.exitButton.setOnClickListener(){
            getActivity()?.finish();
            System.exit(0);

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