package com.example.myapp.data.model

import com.apollographql.apollo3.ApolloClient

class Client {
        val apolloClient = ApolloClient.Builder()
            .addHttpHeader("content-type", "application/json")
            .addHttpHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJsb2dpbiI6ImdpZ2FjaGFkIn0.ij0aD5fWxSBqp4j5pRiiKIiRU5Y5d-Au9Ywj2pdJT3k") // jwt token
            .serverUrl("http://84.201.187.3:8000/graphql")
            .build()
        var isSaved  = false


    companion object {
        val instance = Client()
    }
}

object UtilClient {
    val instance = Client()
}
