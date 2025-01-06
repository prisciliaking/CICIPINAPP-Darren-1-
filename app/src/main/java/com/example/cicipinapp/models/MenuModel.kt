package com.example.cicipinapp.models

data class MenuModel(
    val name: String = "",
    val image: String = "",
    val description: String = "",
    val price: String = ""
)

data class MenuRequest(
    val name: String = "",
    val image: String = "",
    val description: String = "",
    val price: String = "",
    val RestaurantsID: Int
)

data class GetAllMenu(
    val data: List<MenuModel>
)

data class  GetMenuResponse(
    val data: MenuModel
)