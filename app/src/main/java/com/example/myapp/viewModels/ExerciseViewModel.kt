package com.example.myapp.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    var exercises = exerciseRepository.exercises

    var selectedExercises = mutableListOf<ExerciseModel>()

    var filterName = MutableLiveData<String>().apply { postValue("%%")};

    fun startUpdateExercise(idExercise:Int, nameExercise:String, muscle_group:String,exercise_type:String, exercise_image:String, external_id:String) {
        var ex_filter = exercises.value?.filter { it.id == idExercise }
        updateExercise(ExerciseModel(idExercise, nameExercise, muscle_group, exercise_type,exercise_image,
            external_id))
        val exerciseModel = ExerciseModel(idExercise, nameExercise, muscle_group, exercise_type,exercise_image, external_id)
        val client = UtilClient.instance

        var input2 = Converter.toBack(exerciseModel)

        GlobalScope.launch{
            val response2 = client.apolloClient.mutation(CreateOrUpdateExercicesMutation(exercise = input2)).execute()
        }

    }

    fun getExercises(group: String? = null){
        Log.d("exercise", filterName.value!!)
       exercises = exerciseRepository.getExercises(group, filterName.value!!)
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

    fun pickExercise(exerciseModel: ExerciseModel, trainingId: Int) = viewModelScope.launch{
        exerciseRepository.pickExercise(trainingId, exercise_id = exerciseModel.id)
    }




}