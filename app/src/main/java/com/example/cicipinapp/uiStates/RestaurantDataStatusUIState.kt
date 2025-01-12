package com.example.cicipinapp.uiStates

import com.example.cicipinapp.models.RestaurantModel

sealed interface RestaurantDataStatusUIState {
    data class Success(val data: List<RestaurantModel>): RestaurantDataStatusUIState
    object Start: RestaurantDataStatusUIState
    object Loading: RestaurantDataStatusUIState
    data class Failed(val errorMessage:String): RestaurantDataStatusUIState
}