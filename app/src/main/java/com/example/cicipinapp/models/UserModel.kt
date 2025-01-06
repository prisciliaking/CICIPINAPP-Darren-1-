package com.example.cicipinapp.models

data class UserResponse(
    val data: UserModel
)

data class UserModel(
    val id : Int,
    val profile_picture: String,
    val name : String,
    val username: String,
    val email: String,
    val password: String,
    val role: String,
    val token: String?
)