package com.example.shop.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.shop.models.CategoryModel
import com.example.shop.models.ProductModel

@Database(entities = [CategoryModel::class, ProductModel::class],version = 1)
abstract class Database: RoomDatabase() {

    abstract val productDAO : ProductDao
    abstract val categoryDAO : CategoryDao

    companion object{
        @Volatile
        private var INSTANCE : com.example.shop.data.Database? = null
        fun getInstance(context: Context):com.example.shop.data.Database{
            synchronized(this){
                var instance = INSTANCE
                if(instance==null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        com.example.shop.data.Database::class.java,
                        "database"
                    ).build()
                }
                return instance
            }
        }

    }
}