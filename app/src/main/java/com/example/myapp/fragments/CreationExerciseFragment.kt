package com.example.myapp.fragments

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.launch
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.example.CreateOrUpdateExercicesMutation
import com.example.myapp.R
import com.example.myapp.data.Database
import com.example.myapp.databinding.FragmentCreationExerciseBinding
import com.example.myapp.models.ExerciseModel
import com.example.myapp.repositories.ExerciseRepository
import com.example.myapp.utils.Converter
import com.example.myapp.viewModels.ExerciseFactory
import com.example.myapp.viewModels.ExerciseViewModel
import com.example.type.ExerciseReqNameInput
import kotlinx.coroutines.GlobalScope

class CreationExerciseFragment : Fragment() {

    private lateinit var binding: FragmentCreationExerciseBinding
    private var exerciseRepository: ExerciseRepository? = null
    private var exerciseViewModel: ExerciseViewModel? = null
    private var exerciseFactory: ExerciseFactory? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCreationExerciseBinding.inflate(inflater,container, false )
        val exercisesDao = Database.getInstance((context as FragmentActivity).application).exerciseDAO
        exerciseRepository = ExerciseRepository(exercisesDao)
        exerciseFactory = ExerciseFactory(exerciseRepository!!)
        exerciseViewModel = ViewModelProvider(this, exerciseFactory!!).get(ExerciseViewModel::class.java)


        val itemsBodyPart = listOf("Ноги", "Руки", "Спина", "Плечи", "Грудь")
        val itemsTypes = listOf("Количество повторений", "Секунды")
        val adapterBodyPart = ArrayAdapter(context?.applicationContext!!, R.layout.dropdown_item, itemsBodyPart)
        val adapterTypes = ArrayAdapter(context?.applicationContext!!, R.layout.dropdown_item, itemsTypes)
        binding.autoCompleteBodyPart.setAdapter(adapterBodyPart)
        binding.autoCompleteType.setAdapter(adapterTypes)

        binding.pickImage.setOnClickListener(){
            if( Build.VERSION.SDK_INT  >= Build.VERSION_CODES.M){
                pickImageFromGallery()
            }

        }

        binding.createNewExercise.setOnClickListener(){
             val exm =ExerciseModel(0,binding.textExerciseName.text.toString(),
                binding.autoCompleteBodyPart.text?.toString()!!,
                binding.autoCompleteType.text?.toString()!!,
                binding.autoCompleteType.text?.toString()!!)

            exerciseViewModel?.insertExercise(exm)

            val apolloClient = ApolloClient.Builder()
                .addHttpHeader("content-type", "application/json")
                .addHttpHeader("Auth", "token") // jwt token
                .serverUrl("http://84.201.187.3:8000/graphql")
                .build()

            var name1 = binding.textExerciseName.text.toString()
            Log.e("tag1", name1)
            var input2 = Converter.toBack(exm)
            GlobalScope.launch{
                val response2 = apolloClient.mutation(CreateOrUpdateExercicesMutation(exercise = input2)).execute()
                Log.e("tag1", response2.data.toString())
            }
            //Log.e("tag1", exm.toString())
            //Log.e("tag1", input2.toString())

            binding.autoCompleteType.setText("")
            binding.autoCompleteBodyPart.setText("")
            binding.textExerciseName.setText("")

            val transaction  = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.content, ExerciseFragment())
            transaction?.commit()

            //(context as FragmentActivity).supportFragmentManager.beginTransaction().replace(R.id.content, ExerciseFragment()).commit()
        }

        return binding.root
    }


    private fun pickImageFromGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    companion object{
        private val IMAGE_PICK_CODE = 1000;
        private val PERMISSION_CODE = 1001;
        @JvmStatic
        fun newInstance() = CreationExerciseFragment()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        when(requestCode){
            PERMISSION_CODE -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    pickImageFromGallery()
                else
                    Toast.makeText(context?.applicationContext!!, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE)
            binding.imageView.setImageURI(data?.data)

    }



}

