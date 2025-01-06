package com.example.cicipinapp.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
fun RestaurantView(navController: NavController, restaurantViewModel: RestaurantViewModel) {
    var searchText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(19.dp)
            ) {
                Text(text = "Restaurants",
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
            Column(
                modifier =  Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            ){
//                Masukkin Random Resto Card disini
                TextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    placeholder = {
                        Text(
                            "Search Restaurant",
                            color = Color.Gray,
                            modifier = Modifier.fillMaxWidth(), // Agar placeholder terpusat
                            textAlign = TextAlign.Left // Mengatur teks placeholder ke kiri
                        )
                    },
                    leadingIcon = {
                        IconButton(onClick = {
                            // Fungsi untuk aksi pencarian saat ikon diklik
                            // Contoh: searchFunction(searchText) atau aksi lainnya
                            println("Search initiated for: $searchText")
                        }) {
                            Icon(
                                imageVector = Icons.Default.Search, // Ikon pencarian
                                contentDescription = "Search Icon",
                                tint = Color.Gray
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp) // Tinggi TextField
                        .clip(RoundedCornerShape(28.dp)) // Membuat sudut membulat
                        .background(Color(0xFFF5F5F5)) // Warna latar belakang
                        .padding(start = 16.dp, end = 16.dp), // Memberikan padding agar teks tidak tenggelam
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent, // Transparan agar background manual bisa terlihat
                        unfocusedIndicatorColor = Color.Transparent, // Menghilangkan garis bawah default
                        focusedIndicatorColor = Color.Transparent // Menghilangkan garis bawah default
                    ),
                    singleLine = true, // Membatasi hanya satu baris
                    textStyle = TextStyle(
                        color = Color.Black,
                    )
                )


                Spacer(modifier = Modifier.height(8.dp))
                CategoryCardView()
                Spacer(modifier = Modifier.height(8.dp))
                RestaurantCardView(restaurantViewModel)
                Spacer(modifier = Modifier.height(8.dp))

            }
//
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RestaurantPreview() {
    RestaurantView(navController = rememberNavController(),restaurantViewModel = viewModel())
}