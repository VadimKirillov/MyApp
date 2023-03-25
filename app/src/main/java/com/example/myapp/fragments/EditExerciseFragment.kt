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

    private lateinit var binding: PanelEditExerciseBinding
    private lateinit var exerciseRepository: ExerciseRepository
    private var exerciseViewModel: ExerciseViewModel? = null
    private var idExercise:Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PanelEditExerciseBinding.inflate(inflater, container, false)

        idExercise = arguments?.getString("idExercise")?.toInt()
        val baseImage = arguments?.getString("imageExercise")
        binding.textExerciseName.setText(arguments?.getString("nameExercise"))
        baseImage?.let { decodeBase64AndSetImage(it, binding.imageView) }

        val exerciseDao = Database.getInstance((context as FragmentActivity).application).exerciseDAO
        exerciseRepository = ExerciseRepository(exerciseDao)
        val factory = ExerciseFactory(exerciseRepository)
        exerciseViewModel = ViewModelProvider(this, factory).get(ExerciseViewModel::class.java)

        binding.buttonEditExercise.setOnClickListener(this)

        // todo: в OnClick мейби перенести
        binding.pickImage.setOnClickListener(){
            if(Build.VERSION.SDK_INT  >= Build.VERSION_CODES.M){
                pickImageFromGallery()
            }
        }
        activity?.supportFragmentManager
        return binding?.root

    }

    // todo: вынести в статичный класс
    private fun decodeBase64AndSetImage(completeImageData: String, imageView: ImageView) {
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
            binding.imageView.setImageURI(data?.data)
    }
    // todo: в статичный класс
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
            getBase64String(binding.imageView ) ?: arguments?.getString("imageExercise").toString(),
            arguments?.getString("external_id").toString())

        dismiss()

        (context as FragmentActivity).supportFragmentManager.beginTransaction().replace(R.id.content, ExerciseFragment()).commit()
    }

}