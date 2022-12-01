package com.example.myapp.utils

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapp.models.ExerciseModel

class MainViewModel : ViewModel() {
    //val mutableListExercise = MutableLiveData<ArrayList<ExerciseGOModel>>()
    var pref: SharedPreferences? = null
    var currentDay = 0

    fun savePref(key: String, value: Int){
        pref?.edit()?.putInt(key, value)?.apply()
    }

    fun getExerciseCount(): Int{
        return pref?.getInt(currentDay.toString(), 0) ?: 0
    }
}