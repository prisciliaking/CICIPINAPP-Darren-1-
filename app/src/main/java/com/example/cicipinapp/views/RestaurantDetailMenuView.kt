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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.cicipinapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestoDetailMenu() {
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
            Image(
                painter = painterResource(id = R.drawable.restoimage),
                contentDescription = null,
                modifier = Modifier
                    .offset(y = -60.dp)
                    .clip(RoundedCornerShape(25.dp))
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Card(
                modifier = Modifier
                    .offset(y = -125.dp)
                    .padding(start = 20.dp, end = 20.dp)
                    .shadow(8.dp, RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Nama dan Rating Restoran
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "DEPOT ANINA",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(text = "Indonesian Food")
                            Text(text = "4.3 Km From Your Place", fontSize = 12.sp, color = Color.Gray)
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

                    // Deskripsi Restoran
                    Text(
                        text = "Restaurant Description",
                        fontSize = 16.sp,
                        color = Color.DarkGray,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = "Nikmati hidangan lezat yang dibuat dengan bahan segar dan penuh cita rasa di Flavor Haven. Suasana hangat dan pelayanan ramah menjadikan setiap kunjungan pengalaman kuliner yang tak terlupakan.",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Justify
                    )
                }
            }

            //buat menu resto disini
            LazyColumn(
                modifier = Modifier
                    .offset(y = -100.dp)
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxSize()
            ) {
                item {
                    Text(
                        text = "Food",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
                items(3) {
                    MenuItemCard(
                        title = "Bubur Ayam Jakarta",
                        description = "hidangan khas Indonesia yang terdiri dari bubur nasi...",
                        imageRes = R.drawable.cicipinlogo
                    )
                }
                item {
                    Text(
                        text = "Drink",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
                items(2) {
                    MenuItemCard(
                        title = "Fruit Juice",
                        description = "minuman yang terbuat dari buah-buahan atau sayuran segar...",
                        imageRes = R.drawable.restoimage
                    )
                }
            }
        }

        Button(
            onClick = { /* TODO: Aksi Navigasi */ },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(10.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text(
                text = "Get Direction",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

//ini template card setiap menu
@Composable
fun MenuItemCard(title: String, description: String, imageRes: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            modifier = Modifier
                .size(70.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(text = title, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = description, fontSize = 12.sp, color = Color.Gray, maxLines = 2)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RestoDetailPreview() {
    RestoDetailMenu()
}