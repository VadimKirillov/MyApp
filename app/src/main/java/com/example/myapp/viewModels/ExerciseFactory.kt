package com.example.myapp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapp.models.ExerciseModel
import com.example.myapp.repositories.ExerciseRepository

// формально можно использовать фабрику для получения различнхы ViewModel по одному репозиторию
class ExerciseFactory constructor(private val exerciseRepository: ExerciseRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ExerciseViewModel::class.java)) {
            ExerciseViewModel(this.exerciseRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}
