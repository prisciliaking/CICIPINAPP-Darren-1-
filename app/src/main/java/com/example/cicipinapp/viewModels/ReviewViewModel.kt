package com.example.cicipinapp.viewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cicipinapp.models.CreateReviewRequest
import com.example.cicipinapp.models.ReviewModel
import com.example.cicipinapp.repositories.ReviewRepository
import com.example.cicipinapp.uiStates.StringDataStatusUIState
import kotlinx.coroutines.launch


class ReviewViewModel(
    private val reviewRepository: ReviewRepository
) : ViewModel() {

    var reviews by mutableStateOf<List<ReviewModel>>(emptyList())
        private set

    var submissionStatus by mutableStateOf<StringDataStatusUIState>(StringDataStatusUIState.Start)
        private set

    fun getReviewsForRestaurant(restaurantId: Int) {
        viewModelScope.launch {
            try {
                val response = reviewRepository.getReviewsForRestaurant(restaurantId)
                if (response.isSuccessful) {
                    reviews = response.body() ?: emptyList()
                } else {
                    Log.e("ReviewViewModel", "Error fetching reviews: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("ReviewViewModel", "Exception: ${e.message}")
            }
        }
    }

    fun createReview(request: CreateReviewRequest) {
        viewModelScope.launch {
            try {
                val response = reviewRepository.createReview(request)
                if (response.isSuccessful) {
                    submissionStatus = StringDataStatusUIState.Success("Review created successfully")
                } else {
                    submissionStatus = StringDataStatusUIState.Failed("Failed to create review")
                }
            } catch (e: Exception) {
                submissionStatus = StringDataStatusUIState.Failed("An error occurred: ${e.message}")
            }
        }
    }

    fun updateReview(id: Int, request: CreateReviewRequest) {
        viewModelScope.launch {
            try {
                val response = reviewRepository.updateReview(id, request)
                if (response.isSuccessful) {
                    submissionStatus = StringDataStatusUIState.Success("Review updated successfully")
                } else {
                    submissionStatus = StringDataStatusUIState.Failed("Failed to update review")
                }
            } catch (e: Exception) {
                submissionStatus = StringDataStatusUIState.Failed("An error occurred: ${e.message}")
            }
        }
    }

    fun deleteReview(id: Int) {
        viewModelScope.launch {
            try {
                val response = reviewRepository.deleteReview(id)
                if (response.isSuccessful) {
                    submissionStatus = StringDataStatusUIState.Success("Review deleted successfully")
                } else {
                    submissionStatus = StringDataStatusUIState.Failed("Failed to delete review")
                }
            } catch (e: Exception) {
                submissionStatus = StringDataStatusUIState.Failed("An error occurred: ${e.message}")
            }
        }
    }
}

