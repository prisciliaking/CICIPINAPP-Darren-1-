package com.example.cicipinapp.views

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.cicipinapp.viewModels.MenuViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.cicipinapp.R
import com.example.cicipinapp.navigation.Screen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMenuView(
    menuViewModel: MenuViewModel,
    navController: NavHostController
) {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var menuName by remember { mutableStateOf(menuViewModel.menuName) }
    var menuDescription by remember { mutableStateOf(menuViewModel.menuDescription) }
    var menuPrice by remember { mutableStateOf(menuViewModel.menuPrice) }
    val RestaurantsID by menuViewModel.RestaurantsID.collectAsState()
    val context = LocalContext.current

    // Fetch token from SharedPreferences
    val token = remember {
        context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            .getString("auth_token", "") ?: ""
    }

    // Launcher for image picker
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            selectedImageUri = uri
            menuViewModel.imageUrl = uri.toString() // Convert Uri to String
        } else {
            menuViewModel.imageUrl = "" // Handle null case
        }
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
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .clickable { navController.navigate(Screen.AddMenuList.route) }
                )

                Text(
                    text = "Add Menu",
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Insert Image Menu")

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
                    .clickable { imagePickerLauncher.launch("image/*") },
                contentAlignment = Alignment.Center
            ) {
                if (selectedImageUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(selectedImageUri),
                        contentDescription = "Selected Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = "Add Image",
                        tint = Color.White,
                        modifier = Modifier.size(50.dp)
                    )
                }
            }

            Text("Menu Name")
            TextField(
                value = menuName,
                onValueChange = { menuName = it },
                placeholder = { Text("Menu Name") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color(0xFFF5F5F5),
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                )
            )

            Text("Menu Description")
            TextField(
                value = menuDescription,
                onValueChange = { menuDescription = it },
                placeholder = { Text("Menu Description") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color(0xFFF5F5F5),
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                )
            )

            Text("Menu Price")
            TextField(
                value = menuPrice,
                onValueChange = { menuPrice = it },
                placeholder = { Text("Menu Price") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color(0xFFF5F5F5),
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                )
            )

            Button(
                onClick = {
                    menuViewModel.createMenu(
                        navController,
                        token = token,
                        menuName,
                        selectedImageUri.toString(),
                        menuDescription,
                        menuPrice,
                        RestaurantsID
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFB71C1C)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Submit Menu")
            }
        }
    }
}
