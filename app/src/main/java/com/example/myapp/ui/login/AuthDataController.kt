package com.example.myapp.ui.login;
import retrofit2.Call
import retrofit2.http.Body;
import retrofit2.http.POST

data class LoginRequestBody(
        val login: String,
        val password: String,
        val nickname: String,
        val picture: String,
        )

data class ResponseBody(
        val success: Boolean,
        val errors: Array<String>,
        val token: String? = null,
        val picture: String? = null,
        )


interface AuthController {
    @POST("/register")
    fun register(@Body user: LoginRequestBody): Call<ResponseBody>

    @POST("/auth")
    fun auth(@Body user: LoginRequestBody): Call<ResponseBody>
}