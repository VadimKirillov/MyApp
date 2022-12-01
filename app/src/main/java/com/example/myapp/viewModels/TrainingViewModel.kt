package com.example.myapp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp.models.*
import com.example.myapp.repositories.ExerciseRepository
import com.example.myapp.repositories.TrainingRepository
import kotlinx.coroutines.launch

class TrainingViewModel (private val trainingRepository: TrainingRepository) : ViewModel() {

   val trainings = trainingRepository.trainings

   fun startInsert(nameTraining:String, ) {
       insertTraining(TrainingModel(0,nameTraining))
   }

    fun startUpdateTraining(idTraining:Int, nameTraining:String) {
        updateTraining(TrainingModel(idTraining,nameTraining))
    }

    fun startUpdateLine(idTraining:Int, idExercice:Int, count:Int) {
        updateLine(TrainingExerciseModel(idTraining,idExercice,count))
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