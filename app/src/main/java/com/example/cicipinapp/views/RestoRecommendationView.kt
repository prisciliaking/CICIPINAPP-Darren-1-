package com.example.cicipinapp.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.cicipinapp.views.cards.RestoRecommendationCardView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestoRecommendationView(navController: NavController) {
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
                    contentDescription = "Back Icon",
                    modifier = Modifier
                        .size(24.dp) // Ukuran ikon
                        .clip(CircleShape) // Membuat gambar menjadi bulat (opsional untuk ikon back)
                        .clickable {
                            // Logika Back
                            navController.popBackStack()
                        }
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "Recommendation",
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
            RestoRecommendationCardView()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RestoRecommendationPreview() {
    RestoRecommendationView(navController = rememberNavController())
}