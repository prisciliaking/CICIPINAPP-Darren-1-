package com.example.cicipinapp.views.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cicipinapp.R

@Composable
fun RestoRecommendationCardView(){
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            // Image
            Image(
                painter = painterResource(R.drawable.restoimage),
                contentDescription = "Card Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(19f / 7f),
                contentScale = ContentScale.Crop
            )

            // Text and details
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row {
                    Column {
                        // Title
                        Text(
                            text = "Depot Anina",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )

                        Spacer(modifier = Modifier.height(7.dp))
                        // Distance
                        Text(
                            text = "4.3 Km From Your Place",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = 14.sp,
                                color = Color.Gray
                            ),
                            modifier = Modifier.padding(vertical = 4.dp)
                        )

                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 16.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Column(
                        ) {
                            // Rating Section
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Star Icon
                                Image(
                                    painter = painterResource(R.drawable.baseline_star_24),
                                    contentDescription = "Star Icon",
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "4/5",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontSize = 14.sp,
                                        color = Color.Black
                                    )
                                )
                            }

                            Spacer(modifier = Modifier.height(7.dp))


                            // Favorite Icon
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Star Icon
                                Spacer(modifier = Modifier.width(30.dp))
                                Image(
                                    painter = painterResource(R.drawable.baseline_favorite_24),
                                    contentDescription = "Favorite Icon",
                                    modifier = Modifier
                                        .size(20.dp)  // Menentukan ukuran gambar tetap 20dp
                                        .fillMaxWidth()

                                )
                            }

                        }
                    }


                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
    private fun RestoRecommendationCardPreview(){
    RestoRecommendationCardView()
}
