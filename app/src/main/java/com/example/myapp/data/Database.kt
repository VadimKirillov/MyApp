package com.example.myapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapp.models.ExerciseModel

@Database(entities = [ExerciseModel::class],version = 1)
abstract class Database: RoomDatabase() {

    abstract val exerciseDAO : ExerciseDao

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
                        "database"
                    ).build()
                }
                return instance
            }
        }

    }
}