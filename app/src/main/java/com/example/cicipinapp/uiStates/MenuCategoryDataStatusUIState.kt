package com.example.cicipinapp.uiStates

import com.example.cicipinapp.models.MenuCategoryModel

sealed interface MenuCategoryDataStatusUIState {
    data class Success(val data: List<MenuCategoryModel>): MenuCategoryDataStatusUIState
    object Start: MenuCategoryDataStatusUIState
    object Loading: MenuCategoryDataStatusUIState
    data class Failed(val errorMessage: String): MenuCategoryDataStatusUIState
}
