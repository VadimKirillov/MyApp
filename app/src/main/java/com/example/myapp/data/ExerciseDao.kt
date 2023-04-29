package com.example.myapp.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.example.myapp.models.ExerciseModel


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

    @Query("SELECT * FROM exercise_data_table WHERE exercise_name LIKE :name")
    fun getAllExercises(name: String): LiveData<List<ExerciseModel>>

    @Query("SELECT * FROM exercise_data_table WHERE exercise_muscle_group=:group AND exercise_name LIKE :name")
    fun getExercisesByGroup(group: String, name: String): LiveData<List<ExerciseModel>>

   // @Query("SELECT * FROM exercise_data_table WHERE exercise_muscle_group LIKE :name")
    //fun getExercisesByName(name: String): LiveData<List<ExerciseModel>>

    @Query("INSERT INTO training_exercise_data_table(training_id, exercise_id, count) VALUES (:training_id,:exercise_id, 0)")
    fun pickExercise(exercise_id:Int,training_id:Int)

    @Query("SELECT * FROM exercise_data_table")
    open fun loadExercises(): DataSource.Factory<Integer, ExerciseModel>


//    @Transaction
//    @Query("SELECT * FROM training_data_table")
//    fun getTrainingWithExercises(): List<TrainingWithExercises>
    // @Query("SELECT * FROM product_data_table WHERE product_category = 'Одежда' AND product_price = '2000'")
    //fun getClothes(): LiveData<List<ProductModel>>

}