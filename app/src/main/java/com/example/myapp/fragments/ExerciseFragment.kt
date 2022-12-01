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
import com.example.myapp.data.model.UtilClient
import com.example.myapp.databinding.TabExercisesBinding
import com.example.myapp.models.ExerciseModel
import com.example.myapp.repositories.ExerciseRepository
import com.example.myapp.tabs.ExerciseAdapter
import com.example.myapp.utils.Converter
import com.example.myapp.viewModels.ExerciseFactory
import com.example.myapp.viewModels.ExerciseViewModel
import com.example.type.ExerciseInput
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ExerciseFragment : Fragment() {
    private lateinit var binding: TabExercisesBinding
    private var isSaved: Boolean? = null
    private var exerciseRepository: ExerciseRepository? = null
    private var exerciseViewModel: ExerciseViewModel? = null
    private var exerciseFactory: ExerciseFactory? = null
    private var exerciseAdapter: ExerciseAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TabExercisesBinding.inflate(inflater,container, false )
        isSaved = savedInstanceState?.getBoolean("Saved", false) ?: false

        val exercisesDao = Database.getInstance((context as FragmentActivity).application).exerciseDAO
        exerciseRepository = ExerciseRepository(exercisesDao)
        exerciseFactory = ExerciseFactory(exerciseRepository!!)
        exerciseViewModel = ViewModelProvider(this, exerciseFactory!!).get(ExerciseViewModel::class.java)

        val client = UtilClient.instance
        exerciseRepository!!.exercises

        if(!client.isSaved){
            //exerciseViewModel?.deleteAllExercises()

            var input = Optional.present(ExerciseInput())

            GlobalScope.launch{
                val listLocal = exerciseRepository!!.listExercises
                val response = client.apolloClient.query(AllExercicesQuery(input)).execute()
                println("${response.data?.searchExercises?.exercises!!}")
                for(ex in response.data?.searchExercises?.exercises!!){
                    var record = Converter.toLocal(ex)
                    val filtered = listLocal.filter { it.external_id == record.external_id}

                    if (!filtered.isEmpty()){
                        //filtered[0].id
                        record.id = filtered[0].id
                        exerciseViewModel?.updateExercise(record)
                    }

                    else{
                        exerciseViewModel?.insertExercise(Converter.toLocal(ex))
                    }

                }
            }

            //client.isSaved = true
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

        }
        return binding.root
    }

//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        outState.putBoolean("Saved", isSaved ?: true)
//    }

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

        val client = UtilClient.instance

        //var name1 = binding.textExerciseName.text.toString()

        var input2 = Converter.toBack(exerciseModel)
        val exdel = ExerciseInput(id = Optional.present(exerciseModel.external_id!!))
        GlobalScope.launch{
            val response2 = client.apolloClient.mutation(DeleteExercisesMutation(exercise = exdel)).execute()
            Log.e("tag1", response2.data.toString())
        }
    }

    private fun editExercise(exerciseModel:ExerciseModel) {
        val panelEditExercise = EditExerciseFragment()
        val parameters = Bundle()
        parameters.putString("idExercise", exerciseModel.id.toString())
        parameters.putString("nameExercise", exerciseModel.name)
        parameters.putString("typeExercise", exerciseModel.type)
        parameters.putString("imageExercise", exerciseModel.image)
        parameters.putString("muscleGroupExercise", exerciseModel.muscle_group)
        parameters.putString("external_id", exerciseModel.external_id)

        panelEditExercise.arguments = parameters

        panelEditExercise.show((context as FragmentActivity).supportFragmentManager, "editExercise")
    }



    companion object {

        @JvmStatic
        fun newInstance() = ExerciseFragment()
    }
}