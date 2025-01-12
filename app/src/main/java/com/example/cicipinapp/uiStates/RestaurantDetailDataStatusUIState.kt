package com.example.cicipinapp.uiStates

import com.example.cicipinapp.models.RestaurantModel

sealed interface RestaurantDetailDataStatusUIState {
    data class Success(val data: RestaurantModel): RestaurantDetailDataStatusUIState,
        RestaurantDataStatusUIState
    object Loading: RestaurantDetailDataStatusUIState, RestaurantDataStatusUIState
    object Start: RestaurantDetailDataStatusUIState
    data class Failed(val errorMessage: String): RestaurantDetailDataStatusUIState,
        RestaurantDataStatusUIState
}