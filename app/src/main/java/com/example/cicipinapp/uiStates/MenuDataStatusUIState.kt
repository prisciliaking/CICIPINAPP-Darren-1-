package com.example.cicipinapp.uiStates

import com.example.cicipinapp.models.MenuModel

sealed interface MenuDataStatusUIState {
    object Loading : MenuDataStatusUIState
    data class Success(val data: List<MenuModel>) : MenuDataStatusUIState
    data class Error(val message: String) : MenuDataStatusUIState
    object Empty : MenuDataStatusUIState
}
