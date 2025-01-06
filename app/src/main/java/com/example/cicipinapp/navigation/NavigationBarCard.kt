package com.example.cicipinapp.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.cicipinapp.repositories.UserRepository
import com.example.cicipinapp.viewModels.HomeViewModel
import com.example.cicipinapp.viewModels.RestaurantViewModel
import com.example.cicipinapp.views.HomeView
import com.example.cicipinapp.views.ProfileSettingView
import com.example.cicipinapp.views.RestaurantView
import com.example.cicipinapp.views.ReviewView
import com.example.cicipinapp.views.SettingView
import com.example.cicipinapp.views.WishlistView

@Composable
fun MainScreen(navController: NavHostController, restaurantViewModel: RestaurantViewModel) {
    val bottomNavRoutes = listOf("Home", "Wishlist", "Restaurant", "Review")
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    // Scaffold untuk mengelola struktur layar
    Scaffold(
        bottomBar = {
            // Tampilkan BottomNavigationBar hanya jika rute saat ini ada di bottomNavRoutes
            if (currentRoute in bottomNavRoutes) {
                BottomNavigationBar(navController = navController)
            }
        }
    ) { innerPadding ->
        NavigationGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding),
            restaurantViewModel
        )
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf("Home", "Wishlist", "Restaurant", "Review")
    val icons = listOf(
        Icons.Filled.Home,    // Icon for Home
        Icons.Filled.Star,    // Icon for Wishlist
        Icons.Filled.Place,   // Icon for Restaurant
        Icons.Filled.List     // Icon for Review
    )
    val activeColor = Color(0xFFFFC107) // Yellow color for active item
    val inactiveColor = Color.Gray      // Gray color for inactive item

    var selectedItem by remember { mutableStateOf(0) }

    NavigationBar(
        containerColor = Color.White // Set the background color to white
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    navController.navigate(item) // Navigate to selected page
                },
                icon = {
                    Icon(
                        imageVector = icons[index],
                        contentDescription = item,
                        tint = if (selectedItem == index) activeColor else inactiveColor
                    )
                },
                label = {
                    Text(
                        text = item,
                        color = if (selectedItem == index) activeColor else inactiveColor
                    )
                }
            )
        }
    }
}

@Composable
fun NavigationGraph(navController: NavHostController, modifier: Modifier = Modifier, restaurantViewModel: RestaurantViewModel) {
    NavHost(
        navController = navController,
        startDestination = "Home",
        modifier = modifier
    ) {
        composable("Home") { HomeView(navController = navController, restaurantViewModel, context = LocalContext.current) }
        composable("Wishlist") { WishlistView(navController = navController) }
        composable("Restaurant") { RestaurantView(navController = navController, restaurantViewModel) }
        composable("Review") { ReviewView(navController = navController) }

        // Screen lain yang tidak menggunakan BottomNavigationBar
        composable("Settings") { SettingView(navController = navController, restaurantViewModel) }
        composable("ProfileSettings") { ProfileSettingView(navController = navController) }
    }
}
