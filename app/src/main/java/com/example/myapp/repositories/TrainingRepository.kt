package com.example.myapp.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.myapp.data.ExerciseDao
import com.example.myapp.data.TrainingDao
import com.example.myapp.models.*

class TrainingRepository (private val trainingDAO: TrainingDao) {

    //val trainings = trainingDAO.getTrainingWithExercises()
    //var linesLiveData: LiveData<List<LineWithExercises>> = Transformations.map(trainings) { training ->
    //    training.get(0).lines
    //}
//    val allTrainings = trainingDAO.getTrainings()
    fun getTrainingWithExercisesById(id: Int): LiveData<List<TrainingWithExercises>>{
          return trainingDAO.getTrainingWithExercisesById(id)
     }


    fun getTrainings(): LiveData<List<TrainingModel>> {
        return trainingDAO.getTrainings()
    }


    suspend fun insertTraining(trainingModel: TrainingModel){
        trainingDAO.insertTraining(trainingModel)
    }

    suspend fun updateTraining(trainingModel: TrainingModel){
        trainingDAO.updateTraining(trainingModel)
    }

    suspend fun deleteTraining(trainingModel: TrainingModel) {
        trainingDAO.deleteTraining(trainingModel)
    }

    suspend fun deleteAllTrainings(){
        trainingDAO.deleteAllTrainings()
    }

    suspend fun deleteLine(id: Int){
        trainingDAO.deleteLine(id)
    }

    suspend fun updateLine(trainingModel: TrainingExerciseModel){
        trainingDAO.updateLine(trainingModel)
    }


}