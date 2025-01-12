package com.example.cicipinapp.views

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.cicipinapp.viewModels.MenuViewModel
import com.example.cicipinapp.views.cards.MenuCardView
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.cicipinapp.R
import com.example.cicipinapp.models.MenuModel
import com.example.cicipinapp.navigation.Screen
import com.example.cicipinapp.uiStates.MenuDataStatusUIState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MenuCardListView(menuViewModel: MenuViewModel, navController: NavHostController) {
    val menuDataStatus by menuViewModel.menuDataStatus.observeAsState(MenuDataStatusUIState.Loading)

    LaunchedEffect(Unit) {
        menuViewModel.fetchMenuByRestaurantId(3)
    }
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(19.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.baseline_arrow_back_ios_24),
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Add Menu",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // Use innerPadding to ensure content inside Scaffold does not overlap with top bar
        ) {
            // Scrollable content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp)
            ) {
                    when (menuDataStatus) {
                        is MenuDataStatusUIState.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.padding(8.dp))
                    }

                    is MenuDataStatusUIState.Success -> {
                        val data = (menuDataStatus as MenuDataStatusUIState.Success).data

                        // Check if the data is a List<MenuModel> or a single MenuModel
                        when (data) {
                            is List<*> -> {
                                // Handle the case where data is a List<MenuModel>
                                val menuList = data.filterIsInstance<MenuModel>()  // Safely cast to List<MenuModel>
                                LazyColumn(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(bottom = 80.dp) // Leave space for the buttons
                                ) {
                                    items(menuList) { menu ->
                                        MenuCardView(menu = menu, navController)
                                        Spacer(modifier = Modifier.height(8.dp))
                                    }
                                }
                            }
                            is MenuModel -> {
                                // Handle the case where data is a single MenuModel
                                MenuCardView(menu = data, navController)
                            }
                            else -> {
                                // Handle unexpected data types (optional)
                                Text(text = "Unexpected data type")
                            }
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

            // Button at the bottom
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            ) {
                Button(
                    onClick = {
                        navController.navigate(Screen.AddMenu.route)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    shape = RoundedCornerShape(24.dp), // Rounded corners for the button
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp) // Set button height
                ) {
                    Text(
                        text = "Add New Menu",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Save Menu button (fixed at the bottom as well)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 80.dp, start = 16.dp, end = 16.dp) // Adjust the bottom padding
            ) {
                Button(
                    onClick = {
                        navController.navigate(Screen.Setting.route)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    shape = RoundedCornerShape(24.dp), // Rounded corners for the button
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp) // Set button height
                ) {
                    Text(
                        text = "Save Menu",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
