package com.example.myapp.data.model

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
    val login: String,
    val displayName: String,
    val token: String? = null,
)