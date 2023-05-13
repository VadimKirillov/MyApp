package com.example.myapp.fragments

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import com.example.myapp.PickTrainToStartFragment
import com.example.myapp.R
import com.example.myapp.data.AuthRepository
import com.example.myapp.databinding.FragmentProfileBinding
import com.example.myapp.databinding.FragmentStartTrainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Header
import retrofit2.http.Headers
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    val service by lazy {
        Retrofit.Builder()
                    .baseUrl(getString(R.string.server_url))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(UpdateProfileController::class.java)

    }

     fun decodeBase64AndSetImage(completeImageData: String, imageView: ImageView) {
                    val imageDataBytes = completeImageData.substring(completeImageData.indexOf(",") + 1)
                    val stream: InputStream =
                        ByteArrayInputStream(Base64.decode(imageDataBytes.toByteArray(), Base64.DEFAULT))
                    val bitmap = BitmapFactory.decodeStream(stream)
                    imageView.setImageBitmap(bitmap)
                }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // todo: перенести в граф
        binding = FragmentProfileBinding.inflate(inflater,container, false)
        binding.pickImage.setOnClickListener(){
            if(Build.VERSION.SDK_INT  >= Build.VERSION_CODES.M){
                pickImageFromGallery()
            }
        }

        binding.textLogin.text = AuthRepository.user.login
        binding.editTextNickname.setText(AuthRepository.user.displayName)
        decodeBase64AndSetImage(AuthRepository.user.picture, binding.imageView)

        binding.saveChangeProfile.setOnClickListener(){
            binding.textLogin
            val nickname = binding.editTextNickname.text.toString()
            val password = binding.editTextPassword.text.toString()
            val picture = binding.imageView
            val pictureBase64 = getBase64String(picture)
            AuthRepository.user.picture = pictureBase64
            GlobalScope.launch{
                val token = "Bearer ".plus(AuthRepository.user.token)
                val call = service.updateProfile(token, UpdateProfileRequestBody(nickname=nickname, picture=getBase64String(picture)))
                val userResponse: Response<ResponseBody> = call.execute()
            }
            val transaction  = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.content, OptionsFragment())
            transaction?.commit()
        }
        return binding.root
    }
    private fun pickImageFromGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, ProfileFragment.IMAGE_PICK_CODE)
    }

    companion object {
        private val IMAGE_PICK_CODE = 1000;
        private val PERMISSION_CODE = 1001;
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        when(requestCode){
            ProfileFragment.PERMISSION_CODE -> {
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
        if(resultCode == Activity.RESULT_OK && requestCode == ProfileFragment.IMAGE_PICK_CODE)
            binding.imageView.setImageURI(data?.data)
    }

     private fun getBase64String(im: ImageView): String {
            // give your image file url in mCurrentPhotoPath
            // val bitmap = BitmapFactory.decodeResource(getResources(), im.drawable)
            val bitmap = im.drawable.toBitmap()
            val byteArrayOutputStream = ByteArrayOutputStream()
            // In case you want to compress your image, here it's at 40%
            bitmap.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream)
            val byteArray: ByteArray = byteArrayOutputStream.toByteArray()
            return Base64.encodeToString(byteArray, Base64.DEFAULT)!!
        }

}


data class UpdateProfileRequestBody(
        val nickname: String,
        val password: String?=null,
        val picture: String,
        )

data class ResponseBody(
        val success: Boolean,
        val errors: Array<String>,
        )


interface UpdateProfileController {

//    @Headers("Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJsb2dpbiI6ImFmZ2lnYUBtYWlsLnJ1In0.tif-8XbQyKyru4OJKPPajSlRzGyzxn4w0etrfbpmhN0")
    @POST("/update_profile")
    fun updateProfile(@Header("Authorization") authorization: String, @Body user: UpdateProfileRequestBody): Call<ResponseBody>
}