package com.example.myapp.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.CreateOrUpdateExercicesMutation
import com.example.myapp.data.model.UtilClient
import com.example.myapp.models.ExerciseModel
import com.example.myapp.repositories.ExerciseRepository
import com.example.myapp.utils.Converter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ExerciseViewModel (private val exerciseRepository: ExerciseRepository) : ViewModel() {

    val exercises = exerciseRepository.exercises
    val listExercises = exerciseRepository.listExercises

    fun startInsert(nameExercise:String, muscle_group:String,exercise_type:String, exercise_image:String) {
        insertExercise(ExerciseModel(0,nameExercise, muscle_group, exercise_type,exercise_image,null))
    }

    fun startUpdateExercise(idExercise:Int, nameExercise:String, muscle_group:String,exercise_type:String, exercise_image:String, external_id:String) {
        var ex_filter = exercises.value?.filter { it.id == idExercise }
        updateExercise(ExerciseModel(idExercise, nameExercise, muscle_group, exercise_type,exercise_image,
            external_id))
        val exerciseModel =ExerciseModel(idExercise, nameExercise, muscle_group, exercise_type,exercise_image, external_id)
        val client = UtilClient.instance

        var input2 = Converter.toBack(exerciseModel)

        GlobalScope.launch{
            val response2 = client.apolloClient.mutation(CreateOrUpdateExercicesMutation(exercise = input2)).execute()
//            exm.external_id = response2.data?.createExercise?.exercises?.get(0)!!.id
//            exerciseViewModel?.insertExercise(exm)
        }

    }

    fun insertExercise(exerciseModel: ExerciseModel) = viewModelScope.launch{
        exerciseRepository.insertExercise(exerciseModel)
    }

    fun updateExercise(exerciseModel: ExerciseModel) = viewModelScope.launch{
        exerciseRepository.updateExercise(exerciseModel)
    }

    fun deleteExercise(exerciseModel: ExerciseModel) = viewModelScope.launch{
        exerciseRepository.deleteExercise(exerciseModel)
    }

    fun pickExercise(exerciseModel: ExerciseModel) = viewModelScope.launch{
        exerciseRepository.pickExercise(1, exercise_id = exerciseModel.id)
    }


    fun deleteAllExercises() = viewModelScope.launch{
        exerciseRepository.deleteAllExercises()
    }


}