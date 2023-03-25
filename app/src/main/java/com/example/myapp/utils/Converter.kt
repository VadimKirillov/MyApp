package com.example.myapp.utils

import android.util.Log
import com.apollographql.apollo3.api.Optional
import com.example.AllExercicesQuery
import com.example.DeleteExercisesMutation
import com.example.myapp.data.model.UtilClient
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



//        val client = UtilClient.instance
//        exerciseRepository!!.exercises

//        if(!client.isSaved){
//            //exerciseViewModel?.deleteAllExercises()
//
//            var input = Optional.present(ExerciseInput())
//
//            GlobalScope.launch{
//                val listLocal = exerciseRepository!!.listExercises
//                val response = client.apolloClient.query(AllExercicesQuery(input)).execute()
//                println("${response.data?.searchExercises?.exercises!!}")
//                for(ex in response.data?.searchExercises?.exercises!!){
//                    var record = Converter.toLocal(ex)
//                    val filtered = listLocal.filter { it.external_id == record.external_id}
//
//                    if (!filtered.isEmpty()){
//                        //filtered[0].id
//                        record.id = filtered[0].id
//                        exerciseViewModel?.updateExercise(record)
//                    }
//
//                    else{
//                        exerciseViewModel?.insertExercise(Converter.toLocal(ex))
//                    }
//
//                }
//            }
//
//            //client.isSaved = true
//        }


// delete exercise
//val client = UtilClient.instance
//val exdel = ExerciseInput(id = Optional.present(exerciseModel.external_id!!))
//GlobalScope.launch{
//    val response2 = client.apolloClient.mutation(DeleteExercisesMutation(exercise = exdel)).execute()
//    Log.e("tag1", response2.data.toString())
//}

// delete exercises all
//        /*
//        val apolloClient = ApolloClient.Builder()
//            .addHttpHeader("content-type", "application/json")
//            .addHttpHeader("Auth", "token") // jwt token
//            .serverUrl("http://84.201.187.3:8000/graphql")
//            .build()
//
//         */
//
//        //trainingViewModel?.deleteAllTrainings()
//
////        var input = Optional.present(TrainingInput())
////        GlobalScope.launch{
////            val response = apolloClient.query(AllExercicesQuery(input)).execute()
////            println("${response.data?.searchExercises?.exercises!!}")
////            for(ex in response.data?.searchExercises?.exercises!!){
////                //Converter.toLocal(ex)
////                exerciseViewModel?.insertExercise(Converter.toLocal(ex))
////            }
////        }


// delete exercise
//        val apolloClient = ApolloClient.Builder()
//            .addHttpHeader("content-type", "application/json")
//            .addHttpHeader("Auth", "token") // jwt token
//            .serverUrl("http://84.201.187.3:8000/graphql")
//            .build()

//        var name1 = binding.textExerciseName.text.toString()
//        Log.e("tag1", name1)
//        var input2 = Converter.toBack(trainingModel)
//        val exdel = ExerciseReqNameInput(name = trainingModel.name)
////        GlobalScope.launch{
////            val response2 = apolloClient.mutation(DeleteExercisesMutation(exercise = exdel)).execute()
////            Log.e("tag1", response2.data.toString())
////        }


//  insert exercise
//        val client = UtilClient.instance
//
//        var name1 = binding?.textExerciseName?.text.toString()
//        Log.e("tag1", name1)
//
//        var input2 = Converter.toBack()
//
//        GlobalScope.launch{
//            val response2 = client.apolloClient.mutation(CreateOrUpdateExercicesMutation(exercise = input2)).execute()
//            exm.external_id = response2.data?.createExercise?.exercises?.get(0)!!.id
//            exerciseViewModel?.insertExercise(exm)
//        }