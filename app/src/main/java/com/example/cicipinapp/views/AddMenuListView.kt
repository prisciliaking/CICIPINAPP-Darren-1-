package com.example.cicipinapp.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.cicipinapp.viewmodels.MenuViewModel
import com.example.cicipinapp.views.cards.MenuCardView

@Composable
fun MenuCardListView(menuViewModel: MenuViewModel, navController: NavHostController) {
    val menuList by menuViewModel.menuList.observeAsState(emptyList())

    Column(modifier = Modifier.padding(16.dp)) {
        menuList.forEach { menu ->
            MenuCardView(menu = menu)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
