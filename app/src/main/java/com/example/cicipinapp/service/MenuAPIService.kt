package com.example.cicipinapp.service

import com.example.cicipinapp.models.GeneralResponseModel
import com.example.cicipinapp.models.GetAllMenu
import com.example.cicipinapp.models.GetMenuResponse
import com.example.cicipinapp.models.MenuRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface MenuAPIService {
    @POST("/cicipin/menus")
    fun createMenu(@Header("X-API-TOKEN") token: String, @Body menuModel: MenuRequest): Call<GeneralResponseModel>

    @GET("/cicipin/menus")
    fun getAllMenu(): Call<GetAllMenu>

    @GET("/cicipin/menus/{menuId}")
    fun getMenuByID(@Path("menuId") menuId: Int): Call<GetMenuResponse>


    @GET("/cicipin/menus/restaurant/{restaurantId}")
    fun getAllMenuByRestaurantId(@Path("restaurantId") restaurantId: Int): Call<GetAllMenu>

}