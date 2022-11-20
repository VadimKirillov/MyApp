package com.example.myapp.utils

import com.apollographql.apollo3.api.Optional
import com.example.myapp.models.ExerciseModel
import com.example.type.ExerciseInput
import com.example.type.ExerciseReqNameInput

class Converter {
    companion object {
        fun toLocal(back:ExerciseInput): ExerciseModel{
            return ExerciseModel(back._id.toString().toInt(), back.name.toString(), back.`class`.toString(), back.count.toString(),back.picture.toString())
        }
        fun toBack(local : ExerciseModel):ExerciseReqNameInput{
            return ExerciseReqNameInput(name = local.name, picture =  Optional.present(local.image))
        }
    }
}