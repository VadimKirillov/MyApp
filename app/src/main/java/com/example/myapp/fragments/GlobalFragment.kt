package com.example.myapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.myapp.R
import com.example.myapp.adapters.SectionsPagerAdapter
import com.example.myapp.databinding.FragmentGlobalBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class GlobalFragment: Fragment() {
    private lateinit var binding: FragmentGlobalBinding
    private val listOfTitles = arrayListOf<String>()
    private var ab: ActionBar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        
        binding = FragmentGlobalBinding.inflate(inflater,container, false)

        // массив с названиями фрагментов



        binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                //Toast.makeText(context,"Tab clicked:"+tab!!.text,Toast.LENGTH_SHORT).show()
                //binding.viewPager.currentItem = 1
                requireActivity().window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
                ab = (activity as AppCompatActivity).supportActionBar
                ab?.show()
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
        })
        loadTitles()
        setUpViewPagerWithTabLayout()
        addTabLayoutMediator()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewCompat.requestApplyInsets(binding.coord);
    }

    private fun addTabLayoutMediator() {
        TabLayoutMediator(
            binding.tabs, binding.viewPager
        ) { tab: TabLayout.Tab, position: Int ->
            tab.text = listOfTitles[position]
        }.attach()
    }
    private fun loadTitles() {
        listOfTitles.add("Люди")
        listOfTitles.add("Упражнения")
    }
    private fun setUpViewPagerWithTabLayout() {
        val pagerAdapter = SectionsPagerAdapter(context as FragmentActivity, listOfTitles)
        binding.viewPager.adapter = pagerAdapter
        ab?.show()
    }
    companion object {
        @JvmStatic
        fun newInstance() = GlobalFragment()
    }


}