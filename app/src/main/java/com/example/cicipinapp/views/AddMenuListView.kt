package com.example.cicipinapp.views

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.cicipinapp.viewModels.MenuViewModel
import com.example.cicipinapp.views.cards.MenuCardView
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.cicipinapp.R
import com.example.cicipinapp.navigation.Screen
import com.example.cicipinapp.uiStates.MenuDataStatusUIState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MenuCardListView(menuViewModel: MenuViewModel, navController: NavHostController) {
    // Observing the state of the menu
    val menuDataStatus by menuViewModel.menuDataStatus.observeAsState(MenuDataStatusUIState.Loading)

    // Memanggil fetchMenuList saat composable pertama kali diluncurkan
    LaunchedEffect(Unit) {
        menuViewModel.fetchMenuByRestaurantId(3)
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
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Add Menu",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                when (menuDataStatus) {
                    is MenuDataStatusUIState.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.padding(8.dp))
                    }

                    is MenuDataStatusUIState.Success -> {
                        val menuList = (menuDataStatus as MenuDataStatusUIState.Success).data
                        menuList.forEach { menu ->
                            MenuCardView(menu = menu, navController)
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }

                    is MenuDataStatusUIState.Error -> {
                        Text(
                            text = "Error: ${(menuDataStatus as MenuDataStatusUIState.Error).message}",
                            modifier = Modifier.padding(8.dp)
                        )
                    }

                    is MenuDataStatusUIState.Empty -> {
                        Text(
                            text = "No menus available.",
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
                Box {
                    Button(
                        onClick = { navController.navigate(Screen.AddMenu.route) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                        shape = RoundedCornerShape(24.dp), // Membuat tombol dengan sudut membulat
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter) // Align the button at the bottom center
                            .padding(16.dp)
                            .height(48.dp) // Menentukan tinggi tombol
                    ) {
                        Text(
                            text = "Add New Menu",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

        }
    }
}