package com.example.myapp.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myapp.R

object FragmentManager {
    fun setFragment(newFragment: Fragment, act: AppCompatActivity){
        val transaction  = act.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.placeholder, newFragment)
        transaction.commit()
    }
}