package com.example.myapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myapp.models.ExerciseModel
import com.example.myapp.models.TrainingExerciseModel
import com.example.myapp.models.TrainingModel
import com.example.myapp.models.TrainingWithExercises

@Dao
interface TrainingDao {

    @Insert
    suspend fun insertTraining(productModel: TrainingModel)

    @Update
    suspend fun updateTraining(productModel: TrainingModel)

    @Delete
    suspend fun deleteTraining(productModel: TrainingModel)

    @Query("DELETE FROM training_data_table")
    suspend fun deleteAllTrainings()

    //@Query("SELECT * FROM training_data_table")
    //fun getAllTrainings(): LiveData<List<TrainingModel>>

    @Transaction
    @Query("SELECT * FROM training_data_table")
    fun getTrainingWithExercises(): LiveData<List<TrainingWithExercises>>

    @Query("DELETE FROM training_exercise_data_table WHERE training_id = :training_id and exercise_id = :exercise_id")
    fun deleteLine(training_id: Int, exercise_id: Int)

    @Update
    suspend fun updateLine(productModel: TrainingExerciseModel)
}