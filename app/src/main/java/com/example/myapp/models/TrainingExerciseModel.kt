package com.example.myapp.models

import androidx.room.*

@Entity(tableName = "training_exercise_data_table",
foreignKeys =[
    ForeignKey(entity = ExerciseModel::class, parentColumns = ["id"],childColumns = ["exercise_id"], onDelete = ForeignKey.CASCADE),
    ForeignKey(entity = TrainingModel::class, parentColumns = ["id"],childColumns = ["training_id"], onDelete = ForeignKey.CASCADE),
])
data class TrainingExerciseModel (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id : Int,

    @ColumnInfo(name = "training_id")
    var training_id : Int,

    @ColumnInfo(name = "exercise_id")
    var exercise_id : Int,

    @ColumnInfo(name = "count")
    var count : Int,
    )

data class LineWithExercises(
    @Embedded val playlist: TrainingExerciseModel,
    @Relation(
        parentColumn = "exercise_id",
        entityColumn = "id",
    )
    var exercise: ExerciseModel,
)