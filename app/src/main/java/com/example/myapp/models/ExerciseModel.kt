package com.example.myapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.apollographql.apollo3.api.BooleanExpression
import com.example.myapp.repositories.ExerciseRepository
import java.util.ArrayList

@Entity(tableName = "exercise_data_table")
data class ExerciseModel (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id : Int,

    @ColumnInfo(name = "exercise_name")
    var name : String,

    @ColumnInfo(name = "exercise_muscle_group")
    var muscle_group : String,

    @ColumnInfo(name = "exercise_type")
    var type : String,

    @ColumnInfo(name = "exercise_image")
    var image : String,

    @ColumnInfo(name = "external_id", defaultValue = "NULL")
    var external_id : String?

)

//data class Post (
//    var external_id : String,
//    var title : String,
//    var content : String,
//    var attachments : ArrayList<PostAttachment>,
//)
//
//data class PostAttachment (
//    var model: String,
//    var id: Int,
//)