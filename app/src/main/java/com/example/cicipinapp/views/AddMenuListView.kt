package com.example.cicipinapp.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.cicipinapp.viewModels.MenuViewModel
import com.example.cicipinapp.views.cards.MenuCardView
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.livedata.observeAsState
import com.example.cicipinapp.uiStates.MenuDataStatusUIState

@Composable
fun MenuCardListView(menuViewModel: MenuViewModel, navController: NavHostController) {
    // Observing the state of the menu
    val menuDataStatus by menuViewModel.menuDataStatus.observeAsState(MenuDataStatusUIState.Loading)

    // Memanggil fetchMenuList saat composable pertama kali diluncurkan
    LaunchedEffect(Unit) {
        menuViewModel.fetchMenuByRestaurantId(2)
    }

    Column(modifier = Modifier.padding(16.dp)) {
        when (menuDataStatus) {
            is MenuDataStatusUIState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }
            is MenuDataStatusUIState.Success -> {
                val menuList = (menuDataStatus as MenuDataStatusUIState.Success).data
                menuList.forEach { menu ->
                    MenuCardView(menu = menu, navController)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            is MenuDataStatusUIState.Error -> {
                Text(
                    text = "Error: ${(menuDataStatus as MenuDataStatusUIState.Error).message}",
                    modifier = Modifier.padding(16.dp)
                )
            }
            is MenuDataStatusUIState.Empty -> {
                Text(
                    text = "No menus available.",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}
