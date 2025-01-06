package com.example.cicipinapp.service

import com.example.cicipinapp.models.GeneralResponseModel
import com.example.cicipinapp.models.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthenticationAPIService {
    @POST("cicipin/users/register")
    fun register(@Body registerMap: HashMap<String, String> ): Call<UserResponse>

    @POST("cicipin/users/login")
    fun login(@Body loginMap: HashMap<String, String>): Call<UserResponse>

    @POST("cicipin/users/logout")
    fun logout(@Header("X-API-TOKEN") token: String): Call<GeneralResponseModel>

}