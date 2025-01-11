package com.example.cicipinapp.models

data class RestaurantCategoryModel(
    val id: Int,
    val categoryName: String
)
data class CreateRestaurantCategoryRequest(
    val categoryName: String
)
data class UpdateRestaurantCategoryRequest(
    val id: Int,
    val categoryName: String
)
data class DeleteRestaurantCategoryRequest(
    val id: Int,
    val categoryName: String
)

