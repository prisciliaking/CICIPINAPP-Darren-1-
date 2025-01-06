package com.example.cicipinapp.repositories

import com.example.cicipinapp.models.*
import com.example.cicipinapp.service.MenuAPIService
import retrofit2.Call

interface MenuRepository {
    fun getAllMenu(): Call<GetAllMenu>

    fun createMenu(
        token: String,
        name: String,
        image: String,
        description: String,
        price: String,
        restaurantId: Int
    ): Call<GeneralResponseModel>

    fun getMenuById(id: Int): Call<GetMenuResponse>

    // New method for fetching menus by restaurant ID
    fun getMenuByRestaurantId(restaurantId: Int): Call<GetAllMenu>
}

class NetworkMenuRepository(
    private val menuAPIService: MenuAPIService
) : MenuRepository {
    override fun getAllMenu(): Call<GetAllMenu> {
        return menuAPIService.getAllMenu()
    }

    override fun createMenu(
        token: String,
        name: String,
        image: String,
        description: String,
        price: String,
        restaurantId: Int
    ): Call<GeneralResponseModel> {
        return menuAPIService.createMenu(
            token, MenuRequest(name, image, description, price, restaurantId)
        )
    }

    override fun getMenuById(id: Int): Call<GetMenuResponse> {
        return menuAPIService.getMenuByID(id)
    }

    // New implementation for fetching menus by restaurant ID
    override fun getMenuByRestaurantId(restaurantId: Int): Call<GetAllMenu> {
        return menuAPIService.getAllMenuByRestaurantId(restaurantId)
    }
}
