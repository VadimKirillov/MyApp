package com.example.myapp.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.myapp.data.ExerciseDao
import com.example.myapp.models.ExerciseModel


class ExerciseRepository (private val exerciseDAO: ExerciseDao) {

    val exercises = exerciseDAO.getAllExercises()

    suspend fun insertExercise(exerciseModel: ExerciseModel){
        exerciseDAO.insertExercise(exerciseModel)
        //Log.d("ww", exerciseDAO.getTrainingWithExercises()[0].lines[0].toString())
    }

    suspend fun updateExercise(exerciseModel: ExerciseModel){
        exerciseDAO.updateExercise(exerciseModel)
    }

    suspend fun deleteExercise(exerciseModel: ExerciseModel) {
        exerciseDAO.deleteExercise(exerciseModel)
    }

    suspend fun deleteAllExercises(){
        //exerciseDAO.deleteAllExercises()
    }


}