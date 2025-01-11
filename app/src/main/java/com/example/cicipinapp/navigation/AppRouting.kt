package com.example.cicipinapp.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cicipinapp.viewModels.AuthenticationViewModel
import com.example.cicipinapp.views.HomeView
import com.example.cicipinapp.views.ProfileSettingView
import com.example.cicipinapp.views.RegisterView
import com.example.cicipinapp.views.RestaurantView
import com.example.cicipinapp.views.RestoRecommendationView
import com.example.cicipinapp.views.ReviewView
import com.example.cicipinapp.views.SettingView
import com.example.cicipinapp.views.WishlistView
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.cicipinapp.viewModels.RestaurantViewModel
import com.example.cicipinapp.viewModels.MenuViewModel
import com.example.cicipinapp.views.AddMenuView
import com.example.cicipinapp.views.AddRestaurantView
import com.example.cicipinapp.views.LoginView
import com.example.cicipinapp.views.MenuCardListView
import com.example.cicipinapp.views.MenuDetail


@Composable
fun AppRouting(
    authenticationViewModel: AuthenticationViewModel = viewModel(factory = AuthenticationViewModel.Factory),
    restaurantViewModel: RestaurantViewModel = viewModel(factory = RestaurantViewModel.Factory),
    menuViewModel : MenuViewModel = viewModel (factory = MenuViewModel.Factory),

    ) {
    val navController = rememberNavController() // Create a navigation controller

    // Daftar rute yang akan menampilkan BottomNavigationBar

    val bottomNavRoutes = listOf(
        Screen.Home.route,
        Screen.Wishlist.route,
        Screen.Restaurant.route,
        Screen.Review.route
    )

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    Scaffold(
        // Tampilkan BottomNavigationBar hanya jika rute saat ini ada di bottomNavRoutes
        bottomBar = {
            if (currentRoute in bottomNavRoutes) {
                BottomNavigationBar(navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.AddMenuList.route, // Set the starting screen
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Register.route) {
                RegisterView(
                    authenticationViewModel,
                    modifier = Modifier,
                    navController,
                    context = LocalContext.current
                )
            }
            composable(Screen.Login.route) {
                LoginView(
                    authenticationViewModel,
                    modifier = Modifier,
                    navController,
                    context = LocalContext.current
                )
            }
            composable(Screen.Home.route) {
                HomeView(
                    navController,
                    restaurantViewModel,
                    context = LocalContext.current
                ) // Pass the navController to HomeView
            }
            composable(Screen.Wishlist.route) {
                WishlistView(navController) // Screen to navigate to
            }
            composable(Screen.Restaurant.route) {
                RestaurantView(navController, restaurantViewModel) // Screen to navigate to
            }
            composable(Screen.Review.route) {
                ReviewView(navController) // Screen to navigate to
            }
            composable(Screen.RestoRecommendation.route) {
                RestoRecommendationView(navController) // Screen to navigate to
            }
            composable(Screen.Setting.route) {
                SettingView(navController, restaurantViewModel) // Screen to navigate to
            }
            composable(Screen.ProfileSetting.route) {
                ProfileSettingView(navController) // Screen to navigate to
            }
            composable(Screen.AddRestaurant.route) {
                AddRestaurantView(
                    restaurantViewModel,
                    modifier = Modifier,
                    navController,
                    context = LocalContext.current,
                    token = String()
                )
            }

            composable(Screen.AddMenu.route) {
                AddMenuView(
                    menuViewModel,
                    navController
                )
            }

            composable(Screen.AddMenuList.route) {
                MenuCardListView(
                    menuViewModel,
                    navController
                )
            }

            composable(
                route = "menuDetail/{menuId}",
                arguments = listOf(navArgument("menuId") { type = NavType.StringType })
            ) { backStackEntry ->
                val menuId = backStackEntry.arguments?.getString("menuId")?.toIntOrNull() ?: 0
                MenuDetail(menuId = menuId, navController = navController)
            }
        }
    }
}
