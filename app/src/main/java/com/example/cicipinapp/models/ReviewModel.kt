package com.example.cicipinapp.models

data class ReviewModel(
    val id: Int,
    val review: String,
    val rating: Float,
    val description: String,
    val date: String,
    val RestaurantsID: Int
)

data class CreateReviewRequest(
    val review: String,
    val rating: Float,
    val description: String,
    val date: String,
    val RestaurantsID: Int
)

data class ReviewResponse(
    val id: Int,
    val review: String,
    val rating: Float,
    val description: String,
    val RestaurantsID: Int
)
