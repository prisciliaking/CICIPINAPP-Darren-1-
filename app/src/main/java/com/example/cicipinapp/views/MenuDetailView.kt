package com.example.cicipinapp.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cicipinapp.R
import com.example.cicipinapp.models.MenuModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuDetail(
    menu: MenuModel?,
    navController: NavController
) {
    // Show loading while the menu is being fetched
    if (menu == null) {
        LoadingState(navController)
    } else {
        MenuContent(menu = menu, navController = navController)
    }
}

@Composable
fun LoadingState(navController: NavController) {
    // Placeholder for loading state, can be a progress bar or text
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(color = Color.Blue)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Loading menu...", fontSize = 18.sp, color = Color.Black)
    }
}

@Composable
fun MenuContent(menu: MenuModel, navController: NavController) {
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
            // Menu image
            Image(
                painter = painterResource(R.drawable.restoimage), // Default image if no image exists
                contentDescription = menu.image ?: "Menu Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 25.dp, end = 25.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = menu.name,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = menu.description,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    lineHeight = 20.sp
                )
            }
        }
    }
}

