package com.example.cicipinapp.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cicipinapp.R
import com.example.cicipinapp.viewModels.RestaurantViewModel
import com.example.cicipinapp.views.cards.CategoryCardView
import com.example.cicipinapp.views.cards.RestaurantCardView
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantView(
    navController: NavController,
    restaurantViewModel: RestaurantViewModel = viewModel(factory = RestaurantViewModel.Factory)
) {
    val restaurantList by restaurantViewModel.restaurantList.observeAsState(emptyList())

    // Fetch all menus when the view is first launched
    LaunchedEffect(Unit) {
        restaurantViewModel.fetchAllResto()
    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(19.dp)
            ){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(19.dp)
                ) {
                    Text(
                        text = "Restaurants",
                        color = Color.Black,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                CategoryCardView()
            }

        }
    ) { innerPadding ->

        // LazyColumn to display the list of restaurants
        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier.fillMaxSize()
                .background(Color.White)
        ) {
            items(restaurantList) { restaurant ->
                RestaurantCardView(restaurant = restaurant, navController = navController)
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
private fun RestaurantPreview() {
    RestaurantView(navController = rememberNavController(),restaurantViewModel = viewModel())
}