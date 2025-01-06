package com.example.cicipinapp.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cicipinapp.R
import com.example.cicipinapp.views.cards.WishlistCardView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WishlistView(navController: NavController) {
    var selectedTab by remember { mutableStateOf("Wishlist") }
    val items = listOf("Home", "Wishlist", "Find Resto", "Review")
    val icons = listOf(
        Icons.Filled.Home,    // Ikon untuk Home
        Icons.Filled.Star,    // Ikon untuk Wishlist
        Icons.Filled.Place,   // Ikon untuk Find Resto
        Icons.Filled.List     // Ikon untuk Review
    )
    val activeColor = Color(0xFFFFC107) // Warna kuning untuk item aktif
    val inactiveColor = Color.Gray      // Warna abu-abu untuk item tidak aktif
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(19.dp)
            ) {
                Text(text = "Wishlist",
                    color = Color.Black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
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
            WishlistCardView()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WishlistPreview() {
    WishlistView(navController = rememberNavController())
}