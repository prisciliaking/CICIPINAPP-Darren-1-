package com.example.cicipinapp.models

data class MenuCategoryModel(
    val id: Int,
    val categoryName: String
)

data class CreateMenuCategoryRequest(
    val categoryName: String
)

data class MenuCategoryResponse(
    val id: Int,
    val categoryName: String
)
