package com.example.myapp.repositories

import androidx.lifecycle.LiveData
import com.example.myapp.data.ExerciseDao
import com.example.myapp.data.TrainingDao
import com.example.myapp.models.ExerciseModel
import com.example.myapp.models.TrainingExerciseModel
import com.example.myapp.models.TrainingModel
import com.example.myapp.models.TrainingWithExercises

class TrainingRepository (private val trainingDAO: TrainingDao) {

    val trainings = trainingDAO.getTrainingWithExercises()
    val allTrainings = trainingDAO.getTrainings()

    //suspend fun getTrainings(): LiveData<List<TrainingModel>> {
    //    return trainingDAO.getTrainings()
    //}
    
    fun getTrainingWithExercisesById(id: Int): LiveData<List<TrainingWithExercises>>{
         return trainingDAO.getTrainingWithExercisesById(id)
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

    suspend fun deleteLine(training_id: Int, exercise_id: Int){
        trainingDAO.deleteLine(training_id, exercise_id)
    }

    suspend fun updateLine(trainingModel: TrainingExerciseModel){
        trainingDAO.updateLine(trainingModel)
    }


}