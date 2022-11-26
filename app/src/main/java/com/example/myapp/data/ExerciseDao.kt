package com.example.myapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myapp.models.ExerciseModel
import com.example.myapp.models.TrainingWithExercises

@Dao
interface ExerciseDao {

    @Insert
    suspend fun insertExercise(productModel: ExerciseModel)

    @Update
    suspend fun updateExercise(productModel: ExerciseModel)

    @Delete
    suspend fun deleteExercise(productModel: ExerciseModel)

    @Query("DELETE FROM exercise_data_table")
    suspend fun deleteAllExercises()

    @Query("SELECT * FROM exercise_data_table")
    fun getAllExercises(): LiveData<List<ExerciseModel>>

    @Transaction
    @Query("SELECT * FROM training_data_table")
    fun getTrainingWithExercises(): List<TrainingWithExercises>
    // @Query("SELECT * FROM product_data_table WHERE product_category = 'Одежда' AND product_price = '2000'")
    //fun getClothes(): LiveData<List<ProductModel>>

}