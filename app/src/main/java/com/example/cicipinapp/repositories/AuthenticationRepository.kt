package com.example.cicipinapp.repositories

import com.example.cicipinapp.models.UserResponse
import com.example.cicipinapp.service.AuthenticationAPIService
import retrofit2.Call

interface AuthenticationRepository {
    fun register(profile_picture: String, name:String,  username: String, email: String, password: String, role: String ): Call<UserResponse>
    fun login(username: String, password: String): Call<UserResponse>
}

class NetworkAuthenticationRepository(
    private val authenticationAPIService: AuthenticationAPIService
):AuthenticationRepository {
    override fun register(profile_picture: String, name:String,  username: String, email: String, password: String, role: String ): Call<UserResponse> {
        var registerMap = HashMap<String, String>()
        registerMap["profile_picture"] = profile_picture
        registerMap["name"] = name
        registerMap["username"] = username
        registerMap["email"] = email
        registerMap["password"] = password
        registerMap["role"] = role
        return authenticationAPIService.register(registerMap)
    }

    override fun login(username: String, password: String): Call<UserResponse> {
        var loginMap = HashMap<String, String>()
        loginMap["username"] = username
        loginMap["password"] = password
        return authenticationAPIService.login(loginMap)
    }

}