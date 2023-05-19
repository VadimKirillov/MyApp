package com.example.myapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myapp.PostsFragment
import com.example.myapp.fragments.*

class SectionsPagerAdapter(fa: FragmentActivity, private val listOfTitle: List<String>) :
    FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = listOfTitle.size

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return AllUserProfilesFragment()
            1 -> return ExerciseGlobalFragment()
            2 -> return PostsFragment()

        }
        return AllUserProfilesFragment()
    }

}