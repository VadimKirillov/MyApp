package com.example.myapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapp.models.ExerciseModel
import com.example.myapp.models.TrainingExerciseModel
import com.example.myapp.models.TrainingModel

@Database(entities = [TrainingModel::class, ExerciseModel::class,TrainingExerciseModel::class], version = 2)
abstract class Database: RoomDatabase() {

    abstract val exerciseDAO : ExerciseDao
    abstract val trainingDAO : TrainingDao

    companion object{
        @Volatile
        private var INSTANCE : com.example.myapp.data.Database? = null
        fun getInstance(context: Context):com.example.myapp.data.Database{
            synchronized(this){
                var instance = INSTANCE
                if(instance==null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        com.example.myapp.data.Database::class.java,
                        "database1"
                    ).allowMainThreadQueries().build()
                }
                return instance
            }
        }

    }
}