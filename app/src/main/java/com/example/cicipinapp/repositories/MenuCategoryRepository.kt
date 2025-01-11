package com.example.cicipinapp.repositories

import com.example.cicipinapp.models.CreateMenuCategoryRequest
import com.example.cicipinapp.models.MenuCategoryModel
import com.example.cicipinapp.models.MenuCategoryResponse
import com.example.cicipinapp.service.MenuCategoryAPIService
import retrofit2.Response

interface MenuCategoryRepository {
    suspend fun createMenuCategory(request: CreateMenuCategoryRequest): Response<MenuCategoryResponse>
    suspend fun getAllMenuCategories(): Response<List<MenuCategoryModel>>
    suspend fun updateMenuCategory(id: Int, request: CreateMenuCategoryRequest): Response<MenuCategoryResponse>
    suspend fun deleteMenuCategory(id: Int): Response<Unit>
}

class NetworkMenuCategoryRepository(
    private val menuCategoryAPIService: MenuCategoryAPIService
) : MenuCategoryRepository {

    override suspend fun createMenuCategory(request: CreateMenuCategoryRequest): Response<MenuCategoryResponse> {
        return menuCategoryAPIService.createMenuCategory(request)
    }

    override suspend fun getAllMenuCategories(): Response<List<MenuCategoryModel>> {
        return menuCategoryAPIService.getAllMenuCategories()
    }

    override suspend fun updateMenuCategory(id: Int, request: CreateMenuCategoryRequest): Response<MenuCategoryResponse> {
        return menuCategoryAPIService.updateMenuCategory(id, request)
    }

    override suspend fun deleteMenuCategory(id: Int): Response<Unit> {
        return menuCategoryAPIService.deleteMenuCategory(id)
    }
}
