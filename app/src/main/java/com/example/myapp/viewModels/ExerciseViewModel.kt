package com.example.myapp.viewModels

import androidx.lifecycle.*
import androidx.paging.PagedList
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import com.example.CreateOrUpdateExercicesMutation
import com.example.myapp.data.model.UtilClient
import com.example.myapp.models.ExerciseModel
import com.example.myapp.repositories.ExerciseRepository
import com.example.myapp.utils.Converter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import com.apollographql.apollo3.api.Optional
import com.example.AllExercicesQuery
import com.example.type.ExerciseInput
import kotlin.collections.ArrayList


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
    }

    fun uploadExercise(exerciseModel: ExerciseModel){
        var input2 = Converter.toBack(exerciseModel)
        val client = UtilClient.instance

        GlobalScope.launch{
            val response2 = client.apolloClient.mutation(CreateOrUpdateExercicesMutation(exercise = input2)).execute()
            Log.d("query", "Sent exercise")
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

    fun initExercisesRemote(){

        val dataSourceFactory = object : DataSource.Factory<Integer, ExerciseModel>() {
            override fun create(): DataSource<Integer, ExerciseModel> {
                    return PostsDataSource(this@ExerciseViewModel)
                }
            }

        val source = LivePagedListBuilder<Integer, ExerciseModel>(dataSourceFactory, config)
        exercises = Transformations.switchMap(
            DoubleTrigger(filterGroup, filterName),
            { source.build()}
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

class PostsDataSource(val exerciseViewModel: ExerciseViewModel) : PageKeyedDataSource<Integer, ExerciseModel>() {

    var filterName = exerciseViewModel.filterName.value!!.replace("%", "")

    data class ResponseExercises(
        val exercises: MutableList<ExerciseModel>,
        val position: Int,
        val total_count: Int
    )

    suspend fun query(page: Int, size: Int): ResponseExercises{

            val client = UtilClient.instance
            var input = Optional.present(ExerciseInput(name = Optional.present(filterName), global = Optional.present(true)))
            Log.e("query ", "Query exercises");
            val response = client.apolloClient.query(
                AllExercicesQuery(
                    input,
                    page = Optional.present(page),
                    size = Optional.present(size))).execute()

            var exerciseList = mutableListOf<ExerciseModel>()
            if (response.data?.searchExercises?.exercises != null){
                for (line in response.data?.searchExercises?.exercises!!) {
                    exerciseList.add(Converter.toLocal(line))
                }
            }

            val data = ResponseExercises(
                exerciseList,
                response.data?.searchExercises?.position ?: 0,
                response.data?.searchExercises?.total_count ?: 0,
            )
        Log.e("query ", "Query exercises end");
        return data

    }

    override fun loadInitial(
        params: LoadInitialParams<Integer>,
        callback: LoadInitialCallback<Integer, ExerciseModel>
    ) {
        GlobalScope.launch {
            val data = query(page = 1, size = params.requestedLoadSize)
            callback.onResult(data.exercises ?: listOf(),
                Integer(data.position),
                Integer(data.total_count)
            )
        }

        }


        override fun loadAfter(
        params: LoadParams<Integer>,
        callback: LoadCallback<Integer, ExerciseModel>
    ) {
            GlobalScope.launch {
                val data = query(page = params.key.toInt(), size = params.requestedLoadSize)
                callback.onResult(data.exercises ?: listOf(),
                    Integer(data.position),
                )
            }


        }


    override fun loadBefore(
        params: LoadParams<Integer>,
        callback: LoadCallback<Integer, ExerciseModel>
    ) {

        GlobalScope.launch {
            val data = query(page = params.key.toInt(), size = params.requestedLoadSize)
            callback.onResult(data.exercises ?: listOf(),
                Integer(data.position),
            )
        }
    }
}



