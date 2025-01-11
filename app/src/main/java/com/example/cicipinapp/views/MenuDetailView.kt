package com.example.cicipinapp.views


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.cicipinapp.R
import com.example.cicipinapp.viewModels.MenuViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuDetail(menuId: Int, navController: NavController) {
    val viewModel: MenuViewModel = viewModel(factory = MenuViewModel.Factory)
    val menu by viewModel.menuDetail.observeAsState()
    val errorMessage by viewModel.errorMessage.observeAsState()

    // Trigger fetching the menu when the composable is first displayed

    LaunchedEffect(menuId) {
        Log.d("Menu Detail View", "menu id setelah masuk ke launched = $menuId")
        viewModel.fetchMenuById(token = "", menuId)
        Log.d("Menu Detail View", "Menu response setelah di fetch: $menu")
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
                    contentDescription = "Back Button",
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .padding(top = 3.dp)
                        .clickable { navController.popBackStack() }
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Menu Detail",
                    color = Color.Black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(R.drawable.baseline_more_vert_24),
                    contentDescription = "More Options",
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .padding(top = 3.dp)
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding)
        ) {

            when {

                menu != null -> {
                    Log.d("Menu Detail View", "menu response = $menu")
                    // Display menu details
                    Image(
                        painter = rememberAsyncImagePainter(menu?.image),
                        contentDescription = menu?.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 25.dp, end = 25.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = menu?.name ?: "Unknown",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = menu?.description ?: "No description available",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            lineHeight = 20.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Price: $${menu?.price}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )
                    }
                }
                !errorMessage.isNullOrEmpty() -> {
                    // Display error message
                    Text(
                        text = errorMessage!!,
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                else -> {
                    // Show loading state
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//private fun MenuDetailPreview() {
//    MenuDetail()
//}