package com.example.myapp.repositories

import com.example.myapp.data.ExerciseDao
import com.example.myapp.data.TrainingDao
import com.example.myapp.models.ExerciseModel
import com.example.myapp.models.TrainingModel

class TrainingRepository (private val trainingDAO: TrainingDao) {

    val trainings = trainingDAO.getTrainingWithExercises()

    suspend fun insertTraining(trainingModel: TrainingModel){
        trainingDAO.insertTraining(trainingModel)
        //Log.d("ww", exerciseDAO.getTrainingWithExercises()[0].lines[0].toString())
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


}