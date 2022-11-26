package com.example.myapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "training_exercise_data_table", primaryKeys = ["training_id", "exercise_id"], foreignKeys =
[
    ForeignKey(entity = ExerciseModel::class, parentColumns = ["id"],childColumns = ["exercise_id"], onDelete = ForeignKey.CASCADE),
    ForeignKey(entity = TrainingModel::class, parentColumns = ["id"],childColumns = ["training_id"], onDelete = ForeignKey.CASCADE),
])


data class TrainingExerciseModel (
    @ColumnInfo(name = "training_id")
    var training_id : Int,

    @ColumnInfo(name = "exercise_id")
    var exercise_id : Int,

    @ColumnInfo(name = "count")
    var count : Int,

    )