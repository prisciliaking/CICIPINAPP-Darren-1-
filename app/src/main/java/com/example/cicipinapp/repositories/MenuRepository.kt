package com.example.cicipinapp.repositories

import android.util.Log
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
        restaurantsID: Int

    ): Call<GeneralResponseModel>
    fun getMenuById(menuId: Int): Call<GetMenuResponse>
//    fun getMenuById(menuId: Int): Call<GetMenuResponse>



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
        restaurantsID: Int
    ): Call<GeneralResponseModel> {
        return menuAPIService.createMenu(
            token, MenuRequest(name, image, description, price, restaurantsID)
        )
    }

    override fun getMenuById(menuId: Int): Call<GetMenuResponse> {
        return menuAPIService.getMenuByID(menuId)
    }
 

    override fun getMenuByRestaurantId(restaurantId: Int): Call<GetAllMenu> {
        return menuAPIService.getAllMenuByRestaurantId(restaurantId)
    }
}
