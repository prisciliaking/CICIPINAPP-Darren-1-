package com.example.cicipinapp.models


data class GetAllRestaurant(
    val data: List<ResaurantModel>
)

data class GetRestaurantResponse(
    val data: ResaurantModel
)

data class ResaurantModel(
    val name: String = "",
    val address: String = "",
    val longtitude: String = "",
    val latitude: String = "",
    val description: String = ""
)

data class RestaurantRequest(
    val name: String = "",
    val address: String = "",
    val longtitude: String = "",
    val latitude: String = "",
    val description: String = "",
    val UsersID: Int
)