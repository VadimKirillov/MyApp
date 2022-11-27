package com.example.myapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.apollographql.apollo3.ApolloClient
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollographql.apollo3.api.Optional
import com.example.AllExercicesQuery
import com.example.CreateOrUpdateExercicesMutation
import com.example.DeleteExercisesMutation
import com.example.myapp.data.Database
import com.example.myapp.databinding.TabExercisesBinding
import com.example.myapp.models.ExerciseModel
import com.example.myapp.repositories.ExerciseRepository
import com.example.myapp.tabs.ExerciseAdapter
import com.example.myapp.utils.Converter
import com.example.myapp.viewModels.ExerciseFactory
import com.example.myapp.viewModels.ExerciseViewModel
import com.example.type.ExerciseInput
import com.example.type.ExerciseReqNameInput
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ExerciseFragment : Fragment() {
    private lateinit var binding: TabExercisesBinding

    private var exerciseRepository: ExerciseRepository? = null
    private var exerciseViewModel: ExerciseViewModel? = null
    private var exerciseFactory: ExerciseFactory? = null
    private var exerciseAdapter: ExerciseAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TabExercisesBinding.inflate(inflater,container, false )

        val exercisesDao = Database.getInstance((context as FragmentActivity).application).exerciseDAO
        exerciseRepository = ExerciseRepository(exercisesDao)
        exerciseFactory = ExerciseFactory(exerciseRepository!!)
        exerciseViewModel = ViewModelProvider(this, exerciseFactory!!).get(ExerciseViewModel::class.java)

        val apolloClient = ApolloClient.Builder()
            .addHttpHeader("content-type", "application/json")
            .addHttpHeader("Auth", "token") // jwt token
            .serverUrl("http://84.201.187.3:8000/graphql")
            .build()

        exerciseViewModel?.deleteAllExercises()
        var input = Optional.present(ExerciseInput())
        GlobalScope.launch{
            val response = apolloClient.query(AllExercicesQuery(input)).execute()
            println("${response.data?.searchExercises?.exercises!!}")
            for(ex in response.data?.searchExercises?.exercises!!){
                //Converter.toLocal(ex)
                exerciseViewModel?.insertExercise(Converter.toLocal(ex))
            }
        }

        initRecyclerExercises()
        displayExercises()

        binding.createNewExercise.setOnClickListener {
            //val nextFrag = CreationExerciseFragment()

            val transaction  = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(com.example.myapp.R.id.content, CreationExerciseFragment())
            transaction?.commit()
            //(context as FragmentActivity).supportFragmentManager.beginTransaction().replace(com.example.myapp.R.id.content,
            //    CreationExerciseFragment()).commit()
            //getActivity()?.onBackPressed()
            //binding.recyclerCategories.setVisibility(View.GONE);
            //binding.createNewExercise.setVisibility(View.GONE);
        }
        return binding.root
    }

    private fun initRecyclerExercises(){
        binding?.recyclerCategories?.layoutManager = LinearLayoutManager(context)
        exerciseAdapter = ExerciseAdapter({categoryModel: ExerciseModel -> deleteExercise(categoryModel)},
            {categoryModel:ExerciseModel-> editExercise(categoryModel)},
            {categoryModel: ExerciseModel -> pickExercise(categoryModel)})
        binding?.recyclerCategories?.adapter = exerciseAdapter

    }


    private fun displayExercises(){
        exerciseViewModel?.exercises?.observe(viewLifecycleOwner, Observer {
            exerciseAdapter?.setList(it)
            exerciseAdapter?.notifyDataSetChanged()
        })
    }



    private fun pickExercise(exerciseModel: ExerciseModel) {
        exerciseViewModel?.pickExercise(exerciseModel)
    }



    private fun deleteExercise(exerciseModel: ExerciseModel) {
        exerciseViewModel?.deleteExercise(exerciseModel)
        val apolloClient = ApolloClient.Builder()
            .addHttpHeader("content-type", "application/json")
            .addHttpHeader("Auth", "token") // jwt token
            .serverUrl("http://84.201.187.3:8000/graphql")
            .build()


        //var name1 = binding.textExerciseName.text.toString()
        //Log.e("tag1", name1)
        var input2 = Converter.toBack(exerciseModel)
        val exdel = ExerciseReqNameInput(name = exerciseModel.name)
        GlobalScope.launch{
            val response2 = apolloClient.mutation(DeleteExercisesMutation(exercise = exdel)).execute()
            Log.e("tag1", response2.data.toString())
        }
    }

    private fun editExercise(exerciseModel:ExerciseModel) {
        val panelEditExercise = EditExerciseFragment()
        val parameters = Bundle()
        parameters.putString("idExercise", exerciseModel.id.toString())
        parameters.putString("nameExercise", exerciseModel.name)
        parameters.putString("typeExercise", exerciseModel.type)
        parameters.putString("muscleGroupExercise", exerciseModel.muscle_group)
        panelEditExercise.arguments = parameters

        panelEditExercise.show((context as FragmentActivity).supportFragmentManager, "editExercise")
    }



    companion object {

        @JvmStatic
        fun newInstance() = ExerciseFragment()
    }
}