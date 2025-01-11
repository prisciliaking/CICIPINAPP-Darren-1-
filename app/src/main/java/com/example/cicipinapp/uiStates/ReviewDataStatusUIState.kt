package com.example.cicipinapp.uiStates

import com.example.cicipinapp.models.ReviewModel

sealed interface ReviewDataStatusUIState {
    data class Success(val data: List<ReviewModel>): ReviewDataStatusUIState
    object Start: ReviewDataStatusUIState
    object Loading: ReviewDataStatusUIState
    data class Failed(val errorMessage: String): ReviewDataStatusUIState
}