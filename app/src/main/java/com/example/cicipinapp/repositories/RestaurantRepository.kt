package com.example.cicipinapp.repositories

import com.example.cicipinapp.models.GeneralResponseModel
import com.example.cicipinapp.models.GetAllRestaurant
import com.example.cicipinapp.models.GetRestaurantResponse
import com.example.cicipinapp.models.RestaurantRequest
import com.example.cicipinapp.service.RestaurantAPIService
import retrofit2.Call

interface RestaurantRepository {
    fun getAllRestaurants(token: String): Call<GetAllRestaurant>

    fun createRestaurant(
        UsersID: Int,
        token: String,
        name: String,
        address: String,
        longtitude: String,
        latitude: String,
        description: String
    ): Call<GeneralResponseModel>

    fun getRestaurantById(token: String, id: Int): Call<GetRestaurantResponse>
}

class NetworkRestaurantRepository(
    private val RestaurantAPIService: RestaurantAPIService
): RestaurantRepository {
    override fun getAllRestaurants(token: String): Call<GetAllRestaurant> {
        return RestaurantAPIService.getAllRestaurants()
    }

    override fun createRestaurant(
        UsersID: Int,
        token: String,
        name: String,
        address: String,
        longtitude: String,
        latitude: String,
        description: String

    ): Call<GeneralResponseModel> {
        return RestaurantAPIService.createRestaurant(
            token, RestaurantRequest(name, address, longtitude, latitude, description, UsersID)
        )
    }

    override fun getRestaurantById(token: String, id: Int): Call<GetRestaurantResponse> {
        return  RestaurantAPIService.getRestaurantById(token, id)
    }
}