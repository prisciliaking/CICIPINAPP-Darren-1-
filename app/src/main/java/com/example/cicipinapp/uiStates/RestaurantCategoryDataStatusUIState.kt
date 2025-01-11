package com.example.cicipinapp.uiStates

sealed interface RestaurantCategoryDataStatusUIState {
    object Start : StringDataStatusUIState
    object Loading : StringDataStatusUIState
    data class Success(val message: String) : StringDataStatusUIState
    data class Failed(val errorMessage: String) : StringDataStatusUIState
}