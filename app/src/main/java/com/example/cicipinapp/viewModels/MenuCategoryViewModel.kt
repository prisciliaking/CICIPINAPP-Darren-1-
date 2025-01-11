package com.example.cicipinapp.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cicipinapp.models.CreateMenuCategoryRequest
import com.example.cicipinapp.models.MenuCategoryModel
import com.example.cicipinapp.repositories.MenuCategoryRepository
import com.example.cicipinapp.uiStates.MenuCategoryDataStatusUIState
import kotlinx.coroutines.launch

class MenuCategoryViewModel(
    private val menuCategoryRepository: MenuCategoryRepository
) : ViewModel() {

    var menuCategories by mutableStateOf<List<MenuCategoryModel>>(emptyList())
        private set

    var submissionStatus by mutableStateOf<MenuCategoryDataStatusUIState>(MenuCategoryDataStatusUIState.Start)
        private set

    fun getAllMenuCategories() {
        viewModelScope.launch {
            try {
                submissionStatus = MenuCategoryDataStatusUIState.Loading
                val response = menuCategoryRepository.getAllMenuCategories()
                if (response.isSuccessful) {
                    menuCategories = response.body() ?: emptyList()
                    submissionStatus = MenuCategoryDataStatusUIState.Success(menuCategories)
                } else {
                    submissionStatus = MenuCategoryDataStatusUIState.Failed("Failed to fetch menu categories")
                }
            } catch (e: Exception) {
                submissionStatus = MenuCategoryDataStatusUIState.Failed("An error occurred: ${e.message}")
            }
        }
    }

    fun createMenuCategory(request: CreateMenuCategoryRequest) {
        viewModelScope.launch {
            try {
                submissionStatus = MenuCategoryDataStatusUIState.Loading
                val response = menuCategoryRepository.createMenuCategory(request)
                if (response.isSuccessful) {
                    submissionStatus = MenuCategoryDataStatusUIState.Success(menuCategories)
                } else {
                    submissionStatus = MenuCategoryDataStatusUIState.Failed("Failed to create menu category")
                }
            } catch (e: Exception) {
                submissionStatus = MenuCategoryDataStatusUIState.Failed("An error occurred: ${e.message}")
            }
        }
    }

    fun updateMenuCategory(id: Int, request: CreateMenuCategoryRequest) {
        viewModelScope.launch {
            try {
                val response = menuCategoryRepository.updateMenuCategory(id, request)
                if (response.isSuccessful) {
                    submissionStatus = MenuCategoryDataStatusUIState.Success(menuCategories)
                } else {
                    submissionStatus = MenuCategoryDataStatusUIState.Failed("Failed to update menu category")
                }
            } catch (e: Exception) {
                submissionStatus = MenuCategoryDataStatusUIState.Failed("An error occurred: ${e.message}")
            }
        }
    }

    fun deleteMenuCategory(id: Int) {
        viewModelScope.launch {
            try {
                val response = menuCategoryRepository.deleteMenuCategory(id)
                if (response.isSuccessful) {
                    submissionStatus = MenuCategoryDataStatusUIState.Success(menuCategories)
                } else {
                    submissionStatus = MenuCategoryDataStatusUIState.Failed("Failed to delete menu category")
                }
            } catch (e: Exception) {
                submissionStatus = MenuCategoryDataStatusUIState.Failed("An error occurred: ${e.message}")
            }
        }
    }
}
