package com.example.myapp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp.models.ExerciseModel
import com.example.myapp.repositories.ExerciseRepository
import kotlinx.coroutines.launch

//class TrainingViewModel (private val exerciseRepository: ExerciseRepository) : ViewModel() {
//
//    val exercises = exerciseRepository.exercises
//
//
//    fun startInsert(nameExercise:String, muscle_group:String,exercise_type:String, exercise_image:String) {
//        insertExercise(ExerciseModel(0,nameExercise, muscle_group, exercise_type,exercise_image))
//    }
//
//    fun startUpdateExercise(idExercise:Int, nameExercise:String, muscle_group:String,exercise_type:String, exercise_image:String) {
//        updateExercise(ExerciseModel(idExercise, nameExercise, muscle_group, exercise_type,exercise_image))
//    }
//
//    fun insertExercise(exerciseModel: ExerciseModel) = viewModelScope.launch{
//        exerciseRepository.insertExercise(exerciseModel)
//    }
//
//    fun updateExercise(exerciseModel: ExerciseModel) = viewModelScope.launch{
//
//        exerciseRepository.updateExercise(exerciseModel)
//    }
//
//    fun deleteExercise(exerciseModel: ExerciseModel) = viewModelScope.launch{
//
//        exerciseRepository.deleteExercise(exerciseModel)
//    }
//
//    fun deleteAllExercises() = viewModelScope.launch{
//        exerciseRepository.deleteAllExercises()
//    }
//
//
//}