package com.example.cicipinapp.service

import com.example.cicipinapp.models.CreateRestaurantCategoryRequest
import com.example.cicipinapp.models.RestaurantCategoryModel
import com.example.cicipinapp.models.RestaurantCategoryResponse
import retrofit2.Response
import retrofit2.http.*

interface RestaurantCategoryAPIService {

    // Endpoint untuk membuat kategori restoran
    @POST("/cicipin/restaurant-categories")
    suspend fun createRestaurantCategory(@Body request: CreateRestaurantCategoryRequest): Response<RestaurantCategoryResponse>

    // Endpoint untuk mendapatkan semua kategori restoran
    @GET("/cicipin/restaurant-categories")
    suspend fun getRestaurantCategories(): Response<List<RestaurantCategoryModel>>

    // Endpoint untuk memperbarui kategori restoran
    @PUT("/cicipin/restaurant-categories/{id}")
    suspend fun updateRestaurantCategory(@Path("id") id: Int, @Body request: CreateRestaurantCategoryRequest): Response<RestaurantCategoryResponse>

    // Endpoint untuk menghapus kategori restoran
    @DELETE("/cicipin/restaurant-categories/{id}")
    suspend fun deleteRestaurantCategory(@Path("id") id: Int): Response<Unit>
}
