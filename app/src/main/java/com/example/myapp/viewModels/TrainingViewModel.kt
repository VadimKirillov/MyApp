package com.example.myapp.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp.models.*
import com.example.myapp.repositories.ExerciseRepository
import com.example.myapp.repositories.TrainingRepository
import kotlinx.coroutines.launch

class TrainingViewModel (private val trainingRepository: TrainingRepository) : ViewModel() {

//   var trainings = trainingRepository.trainings
   var linesLiveData = trainingRepository.linesLiveData
   var allTrainings = trainingRepository.allTrainings


   fun getTrainingWithExercisesById(id: Int? = null){
        if(id != null){
            linesLiveData = trainingRepository.getTrainingWithExercisesById(id)
        }
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

    fun startUpdateLine(idTraingLine: Int, idExercice:Int, idTraining:Int, count:Int) {
     // TODO: не передаётся id, не работает редактирование упражения
        updateLine(TrainingExerciseModel(idTraingLine, idTraining,idExercice, count))
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
        trainingRepository.deleteLine(trainingModel.playlist.training_id, trainingModel.playlist.exercise_id)
        //trainingRepository.deleteTraining(trainingModel)
    }

    fun updateLine(trainingModel: TrainingExerciseModel) = viewModelScope.launch{
        trainingRepository.updateLine(trainingModel)

    }

    fun deleteAllTrainings() = viewModelScope.launch{
        trainingRepository.deleteAllTrainings()
    }

}