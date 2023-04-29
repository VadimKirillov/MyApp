package com.example.myapp.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.myapp.data.ExerciseDao
import com.example.myapp.models.ExerciseModel


class ExerciseRepository (private val exerciseDAO: ExerciseDao) {
    val exercises = exerciseDAO.getAllExercises()

    fun getExercises(group: String? = null): LiveData<List<ExerciseModel>> {
          if(group != null){
            return exerciseDAO.getExercisesByGroup(group)
          }
          else{
            return exerciseDAO.getAllExercises()
          }
    }

    suspend fun insertExercise(exerciseModel: ExerciseModel){
        exerciseDAO.insertExercise(exerciseModel)
    }

    suspend fun updateExercise(exerciseModel: ExerciseModel){
        exerciseDAO.updateExercise(exerciseModel)
    }

    suspend fun deleteExercise(exerciseModel: ExerciseModel) {
        exerciseDAO.deleteExercise(exerciseModel)
    }

    suspend fun deleteAllExercises(){
        exerciseDAO.deleteAllExercises()
    }

    suspend fun pickExercise(training_id: Int, exercise_id: Int){
        exerciseDAO.pickExercise(exercise_id = exercise_id, training_id = training_id)
    }

    fun loadExercises(): DataSource.Factory<Integer, ExerciseModel> {
       return exerciseDAO.loadExercises()
    }
    
}