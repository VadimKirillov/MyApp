package com.example.myapp.data.model

import com.apollographql.apollo3.ApolloClient
import com.example.myapp.data.AuthRepository

class Client {
        val apolloClient = ApolloClient.Builder()
            .addHttpHeader("content-type", "application/json")
            .addHttpHeader("Authorization", "Bearer ${AuthRepository.user.token}") // jwt token
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
