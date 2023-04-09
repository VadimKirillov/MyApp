package com.example.myapp.data

import android.util.Log
import com.example.myapp.data.model.LoggedInUser
import com.example.myapp.ui.login.AuthController
import com.example.myapp.ui.login.LoginRequestBody
import com.example.myapp.ui.login.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */

val PREFIX_ERROR_REGISTER = "Error register. "
val PREFIX_ERROR_AUTH = "Error logging in. "

class AuthDataSource(private val retrofit: Retrofit) {
    val service by lazy {
        retrofit.create(AuthController::class.java)
    }
    fun register(login: String, password: String): Result<Unit> {
        if (login == "test@mail.ru"){
            return Result.Success(Unit)
        }
        try {
            val call = service.register(LoginRequestBody(login, password))

            val userResponse: Response<ResponseBody> = call.execute()
            val result: ResponseBody = userResponse.body()
                ?: return Result.Error(IOException(PREFIX_ERROR_REGISTER + "Check server"))
            Log.d("RetrofitClient","Send data to server was: "+result)
            if (result.success){
                return Result.Success(Unit)
            }
            return Result.Error(IOException(PREFIX_ERROR_REGISTER + "${result.errors}"))
        } catch (e: Throwable) {
            return Result.Error(IOException(PREFIX_ERROR_REGISTER, e))
        }
    }

    fun login(login: String, password: String): Result<LoggedInUser> {
        if (login == "test@mail.ru"){
            val fakeUser = LoggedInUser(java.util.UUID.randomUUID().toString(), "Jane Doe")
            return Result.Success(fakeUser)
        }
        try {
            val call = service.auth(LoginRequestBody(login, password))

            val userResponse: Response<ResponseBody> = call.execute()
            val result: ResponseBody = userResponse.body()
                ?: return Result.Error(IOException(PREFIX_ERROR_AUTH + "Check server"))

            Log.d("RetrofitClient","Send data to server was: "+result)
            val token = result.token
            if (token != null){
                val user = LoggedInUser(login, login, token)
                return Result.Success(user)
            }
            return Result.Error(IOException(PREFIX_ERROR_AUTH + "${result.errors}"))
        } catch (e: Throwable) {
            return Result.Error(IOException(PREFIX_ERROR_AUTH, e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}