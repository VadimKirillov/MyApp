package com.example.myapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapp.repositories.ExerciseRepository

@Entity(tableName = "exercise_data_table")
data class ExerciseModel (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "exercise_id")
    var id : Int,

    @ColumnInfo(name = "exercise_name")
    var name : String,

    @ColumnInfo(name = "exercise_muscle_group")
    var muscle_group : String,

    @ColumnInfo(name = "exercise_type")
    var type : String,

    @ColumnInfo(name = "exercise_image")
    var image : String

)