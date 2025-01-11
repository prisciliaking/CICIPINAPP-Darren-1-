package com.example.cicipinapp.service

import com.example.cicipinapp.models.CreateMenuCategoryRequest
import com.example.cicipinapp.models.MenuCategoryModel
import com.example.cicipinapp.models.MenuCategoryResponse
import retrofit2.Response
import retrofit2.http.*

interface MenuCategoryAPIService {

    @POST("/cicipin/menu-categories")
    suspend fun createMenuCategory(@Body request: CreateMenuCategoryRequest): Response<MenuCategoryResponse>

    @GET("/cicipin/menu-categories")
    suspend fun getAllMenuCategories(): Response<List<MenuCategoryModel>>

    @PUT("/cicipin/menu-categories/{id}")
    suspend fun updateMenuCategory(@Path("id") id: Int, @Body request: CreateMenuCategoryRequest): Response<MenuCategoryResponse>

    @DELETE("/cicipin/menu-categories/{id}")
    suspend fun deleteMenuCategory(@Path("id") id: Int): Response<Unit>
}
