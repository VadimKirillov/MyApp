package com.example.myapp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapp.repositories.ExerciseRepository
import com.example.myapp.repositories.TrainingRepository

class TrainingFactory constructor(private val trainingRepository: TrainingRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(TrainingViewModel::class.java)) {
            TrainingViewModel(this.trainingRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}