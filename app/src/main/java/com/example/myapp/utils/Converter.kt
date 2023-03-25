package com.example.myapp.utils

import com.apollographql.apollo3.api.Optional
import com.example.AllExercicesQuery
import com.example.myapp.models.ExerciseModel
import com.example.type.ExerciseInput


class Converter {
    companion object {
        fun toLocal(back: AllExercicesQuery.Exercise?): ExerciseModel{
            return ExerciseModel(0, back!!.name.toString(), back.class_exercise.toString(), "ee",back.picture ?: "aa", back.id)
        }

        fun toBack(local : ExerciseModel): ExerciseInput {
            return ExerciseInput(name = Optional.present(local.name), picture =  Optional.present(local.image),
                id = Optional.present(local.external_id))
        }
    }
}

