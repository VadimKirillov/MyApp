package com.example.myapp.utils

import com.apollographql.apollo3.api.Optional
import com.example.AllExercicesQuery
import com.example.myapp.models.ExerciseModel
import com.example.type.ExerciseReqNameInput

class Converter {
    companion object {
        fun toLocal(back: AllExercicesQuery.Exercise?): ExerciseModel{
            return ExerciseModel(0, back!!.name.toString(), "ee", "ee","ee")
        }
        fun toBack(local : ExerciseModel):ExerciseReqNameInput{
            return ExerciseReqNameInput(name = local.name, picture =  Optional.present(local.image))
        }
    }
}