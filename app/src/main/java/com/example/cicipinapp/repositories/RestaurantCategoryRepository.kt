package com.example.cicipinapp.repositories

import com.example.cicipinapp.models.RestaurantCategoryModel
import retrofit2.Response
import retrofit2.http.*

interface RestaurantCategoryApi {
    @GET("restaurant-categories")
    suspend fun getAllRestaurantCategories(): Response<List<RestaurantCategoryModel>>

    @GET("restaurant-categories/{id}")
    suspend fun getRestaurantCategoryById(@Path("id") id: Int): Response<RestaurantCategoryModel>

    @POST("restaurant-categories")
    suspend fun createRestaurantCategory(@Body request: Map<String, String>): Response<String>

    @PUT("restaurant-categories/{id}")
    suspend fun updateRestaurantCategory(
        @Path("id") id: Int,
        @Body request: Map<String, String>
    ): Response<RestaurantCategoryModel>

    @DELETE("restaurant-categories/{id}")
    suspend fun deleteRestaurantCategory(@Path("id") id: Int): Response<String>
}

class RestaurantCategoryRepository(private val api: RestaurantCategoryApi) {
    suspend fun getRestaurantCategories() = api.getAllRestaurantCategories()
    suspend fun getRestaurantCategoryById(id: Int) = api.getRestaurantCategoryById(id)
    suspend fun createRestaurantCategory(categoryName: String) =
        api.createRestaurantCategory(mapOf("categoryName" to categoryName))

    suspend fun updateRestaurantCategory(id: Int, categoryName: String) =
        api.updateRestaurantCategory(id, mapOf("categoryName" to categoryName))

    suspend fun deleteRestaurantCategory(id: Int) = api.deleteRestaurantCategory(id)
}
