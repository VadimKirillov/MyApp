package com.example.myapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    @Transaction
    @Query("SELECT * FROM training_data_table")
    fun getTrainingWithExercises(): LiveData<List<TrainingWithExercises>>

    @Transaction
    @Query("SELECT * FROM training_data_table WHERE id=:id")
    fun getTrainingWithExercisesById(id:Int): LiveData<List<TrainingWithExercises>>

    @Transaction
    @Query("SELECT * FROM training_data_table WHERE id=:id")
    fun getTrainingWithExercisesByIdList(id:Int): List<TrainingWithExercises>

    @Query("SELECT training_data_table.id AS id, training_data_table.training_name as training_name, count(tr_ex.training_id) FROM training_data_table LEFT JOIN training_exercise_data_table as tr_ex ON tr_ex.training_id = training_data_table.id GROUP BY tr_ex.training_id")
    fun getTrainings(): LiveData<List<TrainingModel>>

    @Query("DELETE FROM training_exercise_data_table WHERE id = :id")
    fun deleteLine(id: Int)

    @Update
    suspend fun updateLine(productModel: TrainingExerciseModel)

    //@Query("SELECT * FROM training_data_table")
    //fun getAllTrainings(): LiveData<List<TrainingModel>>
}