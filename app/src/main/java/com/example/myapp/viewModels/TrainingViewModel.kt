package com.example.myapp.viewModels

import androidx.lifecycle.*
import com.example.myapp.data.Post
import com.example.myapp.models.*
import com.example.myapp.repositories.TrainingRepository
import kotlinx.coroutines.launch

class TrainingViewModel (private val trainingRepository: TrainingRepository) : ViewModel() {

//   var trainings = trainingRepository.trainings
   lateinit var linesLiveData: LiveData<List<LineWithExercises>>
   lateinit var linesLiveDataRead: LiveData<List<LineWithExercises>>
   lateinit var allTrainings : LiveData<List<TrainingModel>>

   var nameTraining = MutableLiveData<String>()

   fun getAllTrainings(){
        allTrainings = Transformations.switchMap(nameTraining) { nameTraining ->
            trainingRepository.getTrainings()
        }
   }


   fun getTrainingWithExercisesById(id: Int? = null){
        if(id != null){
            linesLiveData = Transformations.switchMap(nameTraining) { nameTraining ->
                Transformations.map(trainingRepository.getTrainingWithExercisesById(id)) { training ->
                    training.get(0).lines
                }
            }
        }}




       fun getTrainingWithExercisesReadById(post: Post){
           val liveDataList = MutableLiveData<List<LineWithExercises>>()

           val list = mutableListOf<LineWithExercises>( )
           for (line in post.lines){
               list.add(LineWithExercises(
                   exercise = ExerciseModel(id=1, external_id = "", name=line.exercise.name, muscle_group=line.exercise.muscle_group, type=line.exercise.muscle_group, image=line.exercise.picture),
                   playlist = TrainingExerciseModel(1,1,1, line.count,1,1)
               ))
           }

           liveDataList.value = list
           linesLiveData = liveDataList

//        else{
//            linesLiveData = trainingRepository.trainings
//        }
   }


   //fun getTrainings() : LiveData<List<TrainingModel>> {
  //     return trainingRepository.getTrainings()
  // }

   fun startInsert(nameTraining:String, ) {
       insertTraining(TrainingModel(0,nameTraining))
   }

    fun startUpdateTraining(idTraining:Int, nameTraining:String) {
        updateTraining(TrainingModel(idTraining,nameTraining))
    }

    fun startUpdateLine(idTraingLine: Int, idExercice:Int, idTraining:Int, count:Int, rest_time:Int) {
     // TODO: не передаётся id, не работает редактирование упражения
        updateLine(TrainingExerciseModel(idTraingLine, idTraining,idExercice, count,rest_time, 0))
    }

    fun insertTraining(trainingModel: TrainingModel) = viewModelScope.launch{
        trainingRepository.insertTraining(trainingModel)
    }

    fun updateTraining(trainingModel: TrainingModel) = viewModelScope.launch{
        trainingRepository.updateTraining(trainingModel)
    }

   fun deleteTraining(trainingModel: TrainingModel) = viewModelScope.launch{
       trainingRepository.deleteTraining(trainingModel)
   }

    fun deleteLine(trainingModel: LineWithExercises) = viewModelScope.launch{
        trainingRepository.deleteLine(trainingModel.playlist.id)
        //trainingRepository.deleteTraining(trainingModel)
    }

    fun updateLine(trainingModel: TrainingExerciseModel) = viewModelScope.launch{
        trainingRepository.updateLine(trainingModel)
    }

    fun deleteAllTrainings() = viewModelScope.launch{
        trainingRepository.deleteAllTrainings()
    }

}