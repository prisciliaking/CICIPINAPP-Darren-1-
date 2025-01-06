package com.example.cicipinapp.uiStates

import com.example.cicipinapp.models.ResaurantModel

sealed interface RestaurantDataStatusUIState {
    data class Success(val data: List<ResaurantModel>): RestaurantDataStatusUIState
    object Start: RestaurantDataStatusUIState
    object Loading: RestaurantDataStatusUIState
    data class Failed(val errorMessage:String): RestaurantDataStatusUIState
}