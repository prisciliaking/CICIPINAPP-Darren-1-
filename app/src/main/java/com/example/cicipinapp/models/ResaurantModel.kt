package com.example.cicipinapp.models

data class GetAllRestaurant(
    val data: List<RestaurantModel>
)

fun GetRestaurantResponse.toRestaurantModel(): RestaurantModel {
    return this.data
}

data class GetRestaurantResponse(
    val data: RestaurantModel
)

data class RestaurantModel(
    val id: Int = 0,
    val name: String = "",
    val address: String = "",
    val longtitude: String = "",
    val latitude: String = "",
    val description: String = "",
    val UsersID: Int
)

data class RestaurantRequest(
    val name: String = "",
    val address: String = "",
    val longtitude: String = "",
    val latitude: String = "",
    val description: String = "",
    val UsersID: Int
)

data class Restaurant(
    val id: Int = 0,
    val name: String = "",
    val address: String = "",
    val longtitude: String = "",
    val latitude: String = "",
    val description: String = "",
    val UsersID: Int = 0
)

data class RestaurantResponse(
    val data: Restaurant
)