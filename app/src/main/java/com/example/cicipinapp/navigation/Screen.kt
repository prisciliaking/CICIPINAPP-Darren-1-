package com.example.cicipinapp.navigation


sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Wishlist : Screen("wishlist")
    object Restaurant : Screen("restaurant")
    object Review : Screen("review")
    object RestoRecommendation : Screen("resto_recommendation")
    object Setting : Screen("setting")
    object ProfileSetting : Screen("profile_setting")
    object Register : Screen("register")
    object Login: Screen("login")
    object AddRestaurant : Screen("add_restaurant")
}