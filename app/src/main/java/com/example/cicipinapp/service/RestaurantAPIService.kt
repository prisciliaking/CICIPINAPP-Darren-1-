package com.example.cicipinapp.service

import com.example.cicipinapp.models.GeneralResponseModel
import com.example.cicipinapp.models.GetRestaurantResponse
import com.example.cicipinapp.models.RestaurantModel
import com.example.cicipinapp.models.RestaurantRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface RestaurantAPIService {

    @POST("/cicipin/restaurants")
    fun createRestaurant(@Header("X-API-TOKEN") token: String, @Body restaurantModel: RestaurantRequest): Call<GeneralResponseModel>

    @GET("/cicipin/restaurants")
    fun getAllRestaurants(): Call<List<RestaurantModel>>

    @GET("cicipin/restaurants/{id}")
    fun getRestaurant(@Path("id") id: Int): Call<GetRestaurantResponse>
}