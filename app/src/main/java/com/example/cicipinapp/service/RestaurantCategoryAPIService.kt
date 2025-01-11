package com.example.cicipinapp.service

import com.example.cicipinapp.models.CreateRestaurantCategoryRequest
import com.example.cicipinapp.models.RestaurantCategoryModel
import com.example.cicipinapp.models.UpdateRestaurantCategoryRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface RestaurantCategoryAPIService {

    @POST("/cicipin/restaurantCategories")
    suspend fun createRestaurantCategory(@Body request: CreateRestaurantCategoryRequest): Response<RestaurantCategoryModel>

    @PUT("/cicipin/restaurantCategories/{id}")
    suspend fun updateRestaurantCategory(
        @Path("id") id: Int,
        @Body request: UpdateRestaurantCategoryRequest
    ): Response<RestaurantCategoryModel>

    @DELETE("/cicipin/restaurantCategories/{id}")
    suspend fun deleteRestaurantCategory(@Path("id") id: Int): Response<Unit>

    @GET("/cicipin/restaurantCategories")
    suspend fun getRestaurantCategories(): Response<List<RestaurantCategoryModel>>
}

