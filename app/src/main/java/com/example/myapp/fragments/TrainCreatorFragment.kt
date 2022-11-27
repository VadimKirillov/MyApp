package com.example.myapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.example.AllExercicesQuery
import androidx.lifecycle.Observer
import com.example.DeleteExercisesMutation
import com.example.myapp.data.Database
import com.example.myapp.databinding.FragmentTrainCreatorBinding
import com.example.myapp.databinding.TabExercisesBinding
import com.example.myapp.models.ExerciseModel
import com.example.myapp.models.LineWithExercises
import com.example.myapp.models.TrainingExerciseModel
import com.example.myapp.models.TrainingModel
import com.example.myapp.repositories.ExerciseRepository
import com.example.myapp.repositories.TrainingRepository
import com.example.myapp.tabs.ExerciseAdapter
import com.example.myapp.tabs.TrainingAdapter
import com.example.myapp.utils.Converter
import com.example.myapp.viewModels.ExerciseFactory
import com.example.myapp.viewModels.ExerciseViewModel
import com.example.myapp.viewModels.TrainingFactory
import com.example.myapp.viewModels.TrainingViewModel
import com.example.type.ExerciseInput
import com.example.type.ExerciseReqNameInput
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class TrainCreatorFragment : Fragment() {
    private lateinit var binding: FragmentTrainCreatorBinding

    private var trainingRepository: TrainingRepository? = null
    private var trainingViewModel: TrainingViewModel? = null
    private var trainingFactory: TrainingFactory? = null
    private var trainingAdapter: TrainingAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrainCreatorBinding.inflate(inflater,container, false )

        val trainingsDao = Database.getInstance((context as FragmentActivity).application).trainingDAO
        trainingRepository = TrainingRepository(trainingsDao)
        trainingFactory = TrainingFactory(trainingRepository!!)
        trainingViewModel = ViewModelProvider(this, trainingFactory!!).get(TrainingViewModel::class.java)
        /*
        val apolloClient = ApolloClient.Builder()
            .addHttpHeader("content-type", "application/json")
            .addHttpHeader("Auth", "token") // jwt token
            .serverUrl("http://84.201.187.3:8000/graphql")
            .build()

         */

        //trainingViewModel?.deleteAllTrainings()

//        var input = Optional.present(TrainingInput())
//        GlobalScope.launch{
//            val response = apolloClient.query(AllExercicesQuery(input)).execute()
//            println("${response.data?.searchExercises?.exercises!!}")
//            for(ex in response.data?.searchExercises?.exercises!!){
//                //Converter.toLocal(ex)
//                exerciseViewModel?.insertExercise(Converter.toLocal(ex))
//            }
//        }

        initRecyclerTrainings()
        displayTrainings()

        return binding.root
    }

    private fun initRecyclerTrainings(){
        binding?.recyclerCategories?.layoutManager = LinearLayoutManager(context)
        trainingAdapter = TrainingAdapter({categoryModel: LineWithExercises -> deleteTraining(categoryModel)},
            {categoryModel: LineWithExercises -> editTraining(categoryModel)})
        binding?.recyclerCategories?.adapter = trainingAdapter

    }

    private fun displayTrainings(){
        //trainingViewModel?.trainings

        trainingViewModel?.trainings?.observe(viewLifecycleOwner, Observer {
            trainingAdapter?.setList(it.get(0).lines)
            trainingAdapter?.notifyDataSetChanged()
        })
    }


    private fun deleteTraining(trainingModel: LineWithExercises) {
        trainingViewModel?.deleteLine(trainingModel)
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
    }

    private fun editTraining(trainingModel: LineWithExercises) {
        val panelEditCountTrain = EditCountTrainFragment()
        val parameters = Bundle()
        parameters.putString("idExercise", trainingModel.exercise.id.toString())
        parameters.putString("nameExercise", trainingModel.exercise.name)
        parameters.putString("count", trainingModel.playlist.count.toString())
//        parameters.putString("muscleGroupExercise", trainingModel.muscle_group)
        panelEditCountTrain.arguments = parameters
//
        panelEditCountTrain.show((context as FragmentActivity).supportFragmentManager, "editCount")

    }


    companion object {

        @JvmStatic
        fun newInstance() = TrainCreatorFragment()
    }


}