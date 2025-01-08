package com.example.cicipinapp.views.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.cicipinapp.models.MenuModel
import com.example.cicipinapp.navigation.Screen
import java.text.NumberFormat
import java.util.Locale

@Composable
fun MenuCardView(menu: MenuModel, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate(Screen.MenuDetail.createRoute(menu.id.toString()))
            },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Menu Image
            Image(
                painter = rememberAsyncImagePainter(menu.image),
                contentDescription = "Menu Image",
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            // Menu Details
            Column {
                Text(
                    text = menu.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Text(
                    text = menu.description,
                    maxLines = 2,
                    color = Color.Gray
                )
                Text(
                    text = "Price: ${formatToRupiah(menu.price)}",
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

fun formatToRupiah(price: String): String {
    val number = price.toDoubleOrNull() ?: 0.0
    return NumberFormat.getCurrencyInstance(Locale("id", "ID")).format(number)
}

