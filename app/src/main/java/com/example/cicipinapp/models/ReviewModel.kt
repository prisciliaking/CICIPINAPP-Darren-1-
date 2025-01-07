package com.example.cicipinapp.models

data class ReviewModel(
    val id: Int,
    val review: String,
    val rating: Int,
    val description: String,
    val date: String,
    val RestaurantsID: Int
)

data class CreateReviewRequest(
    val review: String,
    val rating: Int,
    val description: String,
    val date: String,
    val RestaurantsID: Int
)

data class UpdateReviewRequest(
    val id: Int,
    val review: String,
    val rating: Int,
    val description: String,
    val RestaurantsID: Int
)

data class DeleteReviewRequest(
    val id: Int
)
