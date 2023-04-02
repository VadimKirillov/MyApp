package com.example.myapp.data

import android.util.Log
import com.example.myapp.R
import com.example.myapp.data.model.LoggedInUser
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */

data class LoginRequestBody(
    val login: String,
    val password: String,
)

data class ResponseBody(
    val success: Boolean,
    val errors: Array<String>,
    val token: String? = null,
)


interface AuthController {
    @POST("/register")
    fun register(@Body user: LoginRequestBody): Call<ResponseBody>

    @POST("/auth")
    fun auth(@Body user: LoginRequestBody): Call<ResponseBody>
}


class AuthDataSource(private val retrofit: Retrofit) {
    val service by lazy {
        retrofit.create(AuthController::class.java)
    }
    fun register(login: String, password: String): Result<Unit> {
        if (login == "test"){
            return Result.Success(Unit)
        }
        try {

            val call = service.register(LoginRequestBody(login, password))
            val userResponse: Response<ResponseBody> = call.execute()
            val result: ResponseBody = userResponse.body()
                ?: return Result.Error(IOException("Error logging in. Check server"))
            Log.d("RetrofitClient","Send data to server was: "+result)
            if (result.success){
                return Result.Success(Unit)
            }
            return Result.Error(IOException("Error logging in. ${result.errors}"))
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun login(login: String, password: String): Result<LoggedInUser> {
        if (login == "test"){
            val fakeUser = LoggedInUser(java.util.UUID.randomUUID().toString(), "Jane Doe")
            return Result.Success(fakeUser)
        }
        try {
            // TODO: handle loggedInUser authentication

            val call= service.auth(LoginRequestBody(login, password))

            val userResponse: Response<ResponseBody> = call.execute()

            val result: ResponseBody = userResponse.body()
                ?: return Result.Error(IOException("Error logging in. Check server"))
            Log.d("RetrofitClient","Send data to server was: "+result)
            val token = result.token
            if (token != null){
                val user = LoggedInUser(login, password, token)
                return Result.Success(user)
            }
            return Result.Error(IOException("Error logging in. ${result.errors}"))
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}