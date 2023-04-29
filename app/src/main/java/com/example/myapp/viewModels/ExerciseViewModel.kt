package com.example.myapp.viewModels

import androidx.lifecycle.*
import androidx.paging.PagedList
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
    val mediatorLiveDataExercises = MediatorLiveData<List<ExerciseModel>>()
    fun getExercisesMediator() : LiveData<List<ExerciseModel>> = mediatorLiveDataExercises

    var filterExercises = MutableLiveData<String>()

    var selectedExercises = mutableListOf<ExerciseModel>()

    lateinit var config : PagedList.Config
    init {
        config = PagedList.Config.Builder()
            .setPageSize(5)
//            .setEnablePlaceholders(false)
            .build()

    }

    fun initAllTeams() {


//        filterExercises.setValue("");
//        exercises = LivePagedListBuilder<Integer, ExerciseModel>(exerciseRepository.loadExercises(), config).build()
//
//        LivePagedListBuilder<Integer, ExerciseModel>(
//            exerciseRepository.loadExercises(), config
//        ).build()

        exercises = Transformations.switchMap<String, PagedList<ExerciseModel>>(
            filterExercises
        ) { input: String? ->
            return@switchMap LivePagedListBuilder<Integer, ExerciseModel>(
                exerciseRepository.loadExercises(), config
            ).build()
        }

    }

    fun startUpdateExercise(idExercise:Int, nameExercise:String, muscle_group:String,exercise_type:String, exercise_image:String, external_id:String) {
//        var ex_filter = exercises.value?. { it.id == idExercise }
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
//        initAllTeams()
//        var list = exerciseRepository.getExercises(group)
//        mediatorLiveDataExercises.addSource(exerciseRepository.getExercises(group)){
//            mediatorLiveDataExercises.postValue( exercises.value)
//            exercises.value = it
//        }
//        var list = exerciseRepository.getExercises(group)
//        list.observeForever {
//            exercises.value = it
//        }
//        exercises.value = listOf(ExerciseModel(100, "test", "test", "test","", null))
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