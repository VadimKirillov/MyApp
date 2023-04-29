package com.example.myapp.viewModels

import androidx.lifecycle.*
import androidx.paging.PagedList
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
import androidx.paging.LivePagedListBuilder


class ExerciseViewModel (private val exerciseRepository: ExerciseRepository) : ViewModel() {
    lateinit var exercises : LiveData<PagedList<ExerciseModel>>
    var selectedExercises = mutableListOf<ExerciseModel>()

    var filterName = MutableLiveData<String>()
    var filterGroup = MutableLiveData<String>()

    var config : PagedList.Config
    init {
        config = PagedList.Config.Builder()
            .setPageSize(5)
//            .setEnablePlaceholders(false)
            .build()
    }

    fun startUpdateExercise(idExercise:Int, nameExercise:String, muscle_group:String,exercise_type:String, exercise_image:String, external_id:String) {
        updateExercise(ExerciseModel(idExercise, nameExercise, muscle_group, exercise_type,exercise_image,
            external_id))
        val exerciseModel = ExerciseModel(idExercise, nameExercise, muscle_group, exercise_type,exercise_image, external_id)
        val client = UtilClient.instance

        var input2 = Converter.toBack(exerciseModel)

        GlobalScope.launch{
            val response2 = client.apolloClient.mutation(CreateOrUpdateExercicesMutation(exercise = input2)).execute()
        }

    }

    class DoubleTrigger<A, B>(a: LiveData<A>, b: LiveData<B>) : MediatorLiveData<Pair<A?, B?>>() {
        init {
            addSource(a) { value = it to b.value }
            addSource(b) { value = a.value to it }
        }
    }

    fun initExercises(){
        Log.d("exercise", filterName.value!!)
        exercises = Transformations.switchMap(
            DoubleTrigger(filterGroup, filterName),
            { LivePagedListBuilder<Integer, ExerciseModel>(exerciseRepository.getExercises(it.first, it.second!!), config).build()}
        )
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