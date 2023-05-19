package com.example.myapp.data

data class Post (
    val postHead: String,
    val picture: String?,
    val text: String?,
    val likes: String?,
    val author: String?,
    val lines: List<ExerciseLine>,
    )
data class ExerciseLine (
    val exercise:Exercise,
    val count:Int
    )

data class Exercise(
    val name: String,
    val picture: String,
    val muscle_group: String,
)
