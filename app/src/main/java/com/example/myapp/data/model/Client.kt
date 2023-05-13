package com.example.myapp.data.model

import com.apollographql.apollo3.ApolloClient

class Client {
        val apolloClient = ApolloClient.Builder()
            .addHttpHeader("content-type", "application/json")
            .addHttpHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJsb2dpbiI6InNzc0Bzc3MucnUifQ.07ZM8QISw2_yTYLT_xoEAottTrk-kazjp25sAduHdN0") // jwt token
            .serverUrl("http://192.168.0.145:8000/graphql")
            .build()
        var isSaved  = false

    companion object {
        val instance = Client()
    }
}

object UtilClient {
    val instance = Client()
}
