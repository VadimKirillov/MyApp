package com.example.myapp.fragments

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.CreateOrUpdateExercicesMutation
import com.example.myapp.R
import com.example.myapp.data.Database
import com.example.myapp.data.model.UtilClient
import com.example.myapp.databinding.PanelEditExerciseBinding
import com.example.myapp.repositories.ExerciseRepository
import com.example.myapp.utils.Converter
import com.example.myapp.viewModels.ExerciseFactory
import com.example.myapp.viewModels.ExerciseViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream

class EditExerciseFragment : DialogFragment(), View.OnClickListener {

    private var binding: PanelEditExerciseBinding? = null
    private var exerciseRepository: ExerciseRepository? = null
    private var exerciseViewModel: ExerciseViewModel? = null
    private var factory: ExerciseFactory? = null
    private var idExercise:Int? = null
    private var baseImage:String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = PanelEditExerciseBinding.inflate(inflater, container, false)

        idExercise = arguments?.getString("idExercise")?.toInt()
        baseImage = arguments?.getString("imageExercise")?.toString()
        binding?.textExerciseName?.setText(arguments?.getString("nameExercise")?.toString())
        baseImage?.let { decodeBase64AndSetImage(it, binding!!.imageView) }

        /*
        binding?.editCategoryExercise?.setText(arguments?.getString("categoryProduct").toString())
        binding?.editPriceExercise?.setText(arguments?.getString("priceProduct").toString())
        */

        val productDao = Database.getInstance((context as FragmentActivity).application).exerciseDAO
        exerciseRepository = ExerciseRepository(productDao)
        factory = ExerciseFactory(exerciseRepository!!)
        exerciseViewModel = ViewModelProvider(this, factory!!).get(ExerciseViewModel::class.java)


        binding?.buttonEditExercise?.setOnClickListener(this)

        binding!!.pickImage.setOnClickListener(){

            if( Build.VERSION.SDK_INT  >= Build.VERSION_CODES.M){
                pickImageFromGallery()
                //binding.textView.text = getBase64String(binding.imageView)
                //binding.textExerciseName.setText(getBase64String(binding.imageView)))
            }
        }
        return binding?.root

    }
    private fun decodeBase64AndSetImage(completeImageData: String, imageView: ImageView) {
        // Incase you're storing into aws or other places where we have extension stored in the starting.
        val imageDataBytes = completeImageData.substring(completeImageData.indexOf(",") + 1)
        val stream: InputStream =
            ByteArrayInputStream(Base64.decode(imageDataBytes.toByteArray(), Base64.DEFAULT))
        val bitmap = BitmapFactory.decodeStream(stream)
        imageView.setImageBitmap(bitmap)
    }

    private fun pickImageFromGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, CreationExerciseFragment.IMAGE_PICK_CODE)
    }

    companion object{
        private val IMAGE_PICK_CODE = 1000;
        private val PERMISSION_CODE = 1001;
        @JvmStatic
        fun newInstance() = EditExerciseFragment()
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
            binding?.imageView?.setImageURI(data?.data)

    }

    private fun getBase64String(im: ImageView): String? {
        // give your image file url in mCurrentPhotoPath
        //val bitmap = BitmapFactory.decodeResource(getResources(), im.drawable)
        val bitmap = im.drawable.toBitmap()
        val byteArrayOutputStream = ByteArrayOutputStream()
        // In case you want to compress your image, here it's at 40%
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream)
        val byteArray: ByteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    override fun onClick(view: View) {
        exerciseViewModel?.startUpdateExercise(idExercise.toString().toInt(), binding?.textExerciseName?.text.toString(),
            arguments?.getString("muscleGroupExercise").toString(),
            arguments?.getString("typeExercise").toString(),
            getBase64String(binding!!.imageView ) ?: arguments?.getString("imageExercise").toString(),
            arguments?.getString("external_id").toString())


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

        dismiss()

        (context as FragmentActivity).supportFragmentManager.beginTransaction().replace(R.id.content, ExerciseFragment()).commit()
    }

}