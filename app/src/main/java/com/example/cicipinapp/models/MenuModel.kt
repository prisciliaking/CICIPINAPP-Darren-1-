package com.example.cicipinapp.models


typealias GetAllMenu = List<MenuModel>

data class  GetMenuResponse(
    val data: MenuModel
)

fun GetMenuResponse.toMenuModel(): MenuModel {
    return this.data
}

data class MenuModel(
    val id: Int,
    val name: String = "",
    val image: String = "",
    val description: String = "",
    val price: String = "",
    val RestaurantsID: Int
)

data class MenuRequest(
    val name: String = "",
    val image: String = "",
    val description: String = "",
    val price: String = "",
    val RestaurantsID: Int
)

data class Menu(
    val id: Int,
    val name: String = "",
    val image: String = "",
    val description: String = "",
    val price: String = "",
    val RestaurantsID: Int
)




