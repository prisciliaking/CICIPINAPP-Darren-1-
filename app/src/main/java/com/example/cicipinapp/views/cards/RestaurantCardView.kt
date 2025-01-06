package com.example.cicipinapp.views.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cicipinapp.R
import com.example.cicipinapp.viewModels.RestaurantViewModel

@Composable
fun RestaurantCardView(restaurantViewModel: RestaurantViewModel){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
//                onClick = onCardClick,
        colors = CardDefaults.cardColors(
            containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(125.dp)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.restoimage),
                    contentDescription = null,
                    modifier = Modifier.size(470.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.padding(top = 8.dp)) {
                Text(
                    text = "restaurant.name",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_star_24),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
//                            Text(text = "${restaurant.rating}", fontSize = 16.sp)
                }
                Spacer(modifier = Modifier.height(4.dp))
//                        Text(text = "restaurant.location", fontSize = 14.sp)
                Spacer(modifier = Modifier.height(6.dp))
                Row {
//                            Text(
//                                text = "${restaurant.distance} km ",
//                                fontSize = 14.sp,
//                                fontWeight = FontWeight.Bold
//                            )
                    Text(
                        text = "from your location",
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RestaurantCardPreview(){
    RestaurantCardView(restaurantViewModel = viewModel())
}
