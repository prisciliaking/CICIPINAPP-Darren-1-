package com.example.cicipinapp.service

import com.example.cicipinapp.models.GeneralResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.POST

interface UserAPIService {
    @DELETE("cicipin/users/logout")
    fun logout(@Body request: Map<String, Int>): Call<GeneralResponseModel>
}