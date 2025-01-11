package com.example.cicipinapp.viewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cicipinapp.models.RestaurantCategoryModel
import com.example.cicipinapp.repositories.RestaurantCategoryRepository
import com.example.cicipinapp.uiStates.StringDataStatusUIState
import kotlinx.coroutines.launch

class RestaurantCategoryViewModel(
    private val restaurantCategoryRepository: RestaurantCategoryRepository
) : ViewModel() {

    var restaurantCategories = mutableStateListOf<RestaurantCategoryModel>()
        private set

    var submissionStatus by mutableStateOf<StringDataStatusUIState>(StringDataStatusUIState.Start)
        private set

    fun getRestaurantCategories() {
        viewModelScope.launch {
            try {
                val response = restaurantCategoryRepository.getRestaurantCategories()
                if (response.isSuccessful) {
                    response.body()?.let { categories ->
                        restaurantCategories.clear()
                        restaurantCategories.addAll(categories)
                    } ?: run {
                        Log.e("RestaurantCategoryViewModel", "Response body is null")
                    }
                } else {
                    Log.e(
                        "RestaurantCategoryViewModel",
                        "Error fetching categories: ${response.message()}"
                    )
                }
            } catch (e: Exception) {
                Log.e("RestaurantCategoryViewModel", "Exception: ${e.message}")
            }
        }
    }

    fun createRestaurantCategory(categoryName: String) {
        viewModelScope.launch {
            try {
                val response = restaurantCategoryRepository.createRestaurantCategory(categoryName)
                if (response.isSuccessful) {
                    submissionStatus =
                        StringDataStatusUIState.Success("Category created successfully")
                    getRestaurantCategories() // Refresh data
                } else {
                    submissionStatus =
                        StringDataStatusUIState.Failed("Failed to create category")
                }
            } catch (e: Exception) {
                submissionStatus =
                    StringDataStatusUIState.Failed("An error occurred: ${e.message}")
            }
        }
    }

    fun updateRestaurantCategory(id: Int, categoryName: String) {
        viewModelScope.launch {
            try {
                val response =
                    restaurantCategoryRepository.updateRestaurantCategory(id, categoryName)
                if (response.isSuccessful) {
                    submissionStatus =
                        StringDataStatusUIState.Success("Category updated successfully")
                    getRestaurantCategories() // Refresh data
                } else {
                    submissionStatus =
                        StringDataStatusUIState.Failed("Failed to update category")
                }
            } catch (e: Exception) {
                submissionStatus =
                    StringDataStatusUIState.Failed("An error occurred: ${e.message}")
            }
        }
    }

    fun deleteRestaurantCategory(id: Int) {
        viewModelScope.launch {
            try {
                val response = restaurantCategoryRepository.deleteRestaurantCategory(id)
                if (response.isSuccessful) {
                    submissionStatus =
                        StringDataStatusUIState.Success("Category deleted successfully")
                    getRestaurantCategories() // Refresh data
                } else {
                    submissionStatus =
                        StringDataStatusUIState.Failed("Failed to delete category")
                }
            } catch (e: Exception) {
                submissionStatus =
                    StringDataStatusUIState.Failed("An error occurred: ${e.message}")
            }
        }
    }
}
