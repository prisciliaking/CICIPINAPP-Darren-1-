package com.example.cicipinapp.views

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.lazy.LazyColumn
import com.example.cicipinapp.R
import com.example.cicipinapp.viewModels.RestaurantViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRestaurantView(
    restaurantViewModel: RestaurantViewModel,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    context: Context,
    token: String
) {
    var textName by remember { mutableStateOf(restaurantViewModel.nameInput) }
    var textAddress by remember { mutableStateOf(restaurantViewModel.addressInput) }
    var textLatitude by remember { mutableStateOf(restaurantViewModel.latitudeInput) }
    var textLongitude by remember { mutableStateOf(restaurantViewModel.longtitudeInput) }
    var textDescription by remember { mutableStateOf(restaurantViewModel.descriptionInput) }
    val userID by restaurantViewModel.userID.collectAsState()
    var userIDInt by remember { mutableStateOf(userID) }

    var category by remember { mutableStateOf("") }

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
                    text = "Add Restaurant",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text("Restaurant Name")
            }
            item {
                TextField(
                    value = textName,
                    onValueChange = { textName = it },
                    placeholder = { Text("Restaurant Name") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color(0xFFF5F5F5),
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    )
                )
            }
            item {
                Text("Restaurant Address")
            }
            item {
                TextField(
                    value = textAddress,
                    onValueChange = { textAddress = it },
                    placeholder = { Text("Restaurant Address") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color(0xFFF5F5F5),
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    )
                )
            }
            item {
                Text("Restaurant Description")
            }
            item {
                TextField(
                    value = textDescription,
                    onValueChange = { textDescription = it },
                    placeholder = { Text("Restaurant Description") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color(0xFFF5F5F5),
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    )
                )
            }
            item {
                Text("Restaurant Coordinate")
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                ) {
                    TextField(
                        value = textLongitude,
                        onValueChange = { textLongitude = it },
                        placeholder = { Text("Longitude") },
                        shape = RoundedCornerShape(8.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color(0xFFF5F5F5),
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent
                        ),
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    TextField(
                        value = textLatitude,
                        onValueChange = { textLatitude = it },
                        placeholder = { Text("Latitude") },
                        shape = RoundedCornerShape(8.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color(0xFFF5F5F5),
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                Button(
                    onClick = {
                        restaurantViewModel.createRestaurant(navController, token = token, textName, textAddress, textLatitude, textLongitude, textDescription, userIDInt)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFB71C1C)
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Next")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AddRestaurantPreview() {
    AddRestaurantView(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        restaurantViewModel = viewModel(),
        navController = rememberNavController(),
        context = LocalContext.current,
        token = ""
    )
}
