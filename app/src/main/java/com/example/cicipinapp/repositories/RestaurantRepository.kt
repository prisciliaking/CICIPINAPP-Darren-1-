package com.example.cicipinapp.repositories

import com.example.cicipinapp.models.GeneralResponseModel
import com.example.cicipinapp.models.GetRestaurantResponse
import com.example.cicipinapp.models.RestaurantModel
import com.example.cicipinapp.models.RestaurantRequest
import com.example.cicipinapp.service.RestaurantAPIService
import retrofit2.Call

interface RestaurantRepository {
    fun getAllRestaurants(): Call<List<RestaurantModel>>
    fun createRestaurant(
        UsersID: Int,
        token: String,
        name: String,
        address: String,
        longtitude: String,
        latitude: String,
        description: String
    ): Call<GeneralResponseModel>

    fun getRestaurantById(restaurantID: Int): Call<GetRestaurantResponse>

}

class NetworkRestaurantRepository(
    private val restaurantAPIService: RestaurantAPIService
): RestaurantRepository {
    override fun getAllRestaurants(): Call<List<RestaurantModel>> {
        return restaurantAPIService.getAllRestaurants()
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
        return restaurantAPIService.createRestaurant(
            token, RestaurantRequest(name, address, longtitude, latitude, description, UsersID)
        )
    }

    override fun getRestaurantById(restaurantID: Int): Call<GetRestaurantResponse> {
        return restaurantAPIService.getRestaurant(restaurantID)
    }



}