package com.example.cicipinapp.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cicipinapp.R
import com.example.cicipinapp.models.MenuModel
import com.example.cicipinapp.models.RestaurantModel
import com.example.cicipinapp.uiStates.MenuDataStatusUIState
import com.example.cicipinapp.viewModels.MenuViewModel
import com.example.cicipinapp.viewModels.RestaurantViewModel
import com.example.cicipinapp.views.cards.MenuCardView
import com.example.cicipinapp.views.cards.RestaurantCardView


@Composable
fun RestoDetailsView(restaurant: RestaurantModel, navController: NavController, menuViewModel: MenuViewModel) {
    val menuDataStatus by menuViewModel.menuDataStatus.observeAsState(MenuDataStatusUIState.Loading)

    LaunchedEffect(Unit) {
        menuViewModel.fetchMenuByRestaurantId(restaurant.id) // Fetch menu items by restaurant ID
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Restaurant Image
            Image(
                painter = painterResource(id = R.drawable.restoimage), // Replace with appropriate image resource
                contentDescription = null,
                modifier = Modifier
                    .offset(y = -60.dp)
                    .clip(RoundedCornerShape(25.dp))
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )

            // Restaurant Details Card
            Card(
                modifier = Modifier
                    .offset(y = -125.dp)
                    .padding(start = 20.dp, end = 20.dp)
                    .shadow(8.dp, RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Name and Rating
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = restaurant.name,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(text = "Indonesian Food") // Static label for cuisine type (can be dynamic if needed)
                            Text(text = restaurant.address, fontSize = 12.sp, color = Color.Gray)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFD300))
                        ) {
                            Text(
                                text = "4★",
                                modifier = Modifier.padding(8.dp),
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = Color.White
                            )
                        }
                    }

                    Divider(
                        color = Color.Gray,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    // Restaurant Description
                    Text(
                        text = "Restaurant Description",
                        fontSize = 16.sp,
                        color = Color.DarkGray,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = restaurant.description,
                        fontSize = 12.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Justify
                    )
                }
            }

            // LazyColumn to show Menu Cards
            when (menuDataStatus) {
                is MenuDataStatusUIState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.padding(8.dp))
                }
                is MenuDataStatusUIState.Success -> {
                    val menuList = (menuDataStatus as MenuDataStatusUIState.Success).data

                    // Ensure menuList is a List<MenuModel>
                    if (menuList is List<*>) {
                        val filteredMenuList = menuList.filterIsInstance<MenuModel>() // Safe cast to List<MenuModel>

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 16.dp, end = 16.dp)
                                .offset(y = -100.dp) // Offset to avoid overlapping with restaurant details
                        ) {
                            items(filteredMenuList) { menu ->
                                // menu is of type MenuModel now
                                MenuCardView(menu = menu, navController = navController)
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    } else {
                        // Handle the case if menuList is not a List<MenuModel>
                        Text(text = "Error: Menu data is in an unexpected format.")
                    }
                }
                is MenuDataStatusUIState.Error -> {
                    Text(
                        text = "Error: ${(menuDataStatus as MenuDataStatusUIState.Error).message}",
                        modifier = Modifier.padding(8.dp)
                    )
                }
                is MenuDataStatusUIState.Empty -> {
                    Text(
                        text = "No menus available.",
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }

        }

        // Button to navigate to the map or details screen
        Button(
            onClick = {
                // Implement navigation to map or details screen here
                // navController.navigate("map/${restaurant.id}")
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(10.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text(
                text = "View On Map",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
