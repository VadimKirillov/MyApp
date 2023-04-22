package com.example.myapp.models

import androidx.lifecycle.LiveData
import androidx.room.*

@Entity(tableName = "training_data_table")
data class TrainingModel (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id : Int,

    @ColumnInfo(name = "training_name")
    var name : String,


)


data class TrainingWithExercises(

    @Embedded val playlist: TrainingModel,

    @Relation(
        parentColumn = "id",
        entityColumn = "training_id",
        entity = TrainingExerciseModel::class
    )
    var lines: List<LineWithExercises>

)

