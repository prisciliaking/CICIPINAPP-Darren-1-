package com.example.cicipinapp.viewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavHostController
import com.example.cicipinapp.CicipInApp
import com.example.cicipinapp.enums.PagesEnum
import com.example.cicipinapp.models.ErrorModel
import com.example.cicipinapp.models.GeneralResponseModel
import com.example.cicipinapp.models.GetAllRestaurant
import com.example.cicipinapp.repositories.RestaurantRepository
import com.example.cicipinapp.repositories.UserRepository
import com.example.cicipinapp.uiStates.RestaurantDataStatusUIState
import com.example.cicipinapp.uiStates.StringDataStatusUIState
import com.google.gson.Gson
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
class HomeViewModel(
    private val userRepository: UserRepository,
    private  val restaurantRepository: RestaurantRepository
): ViewModel() {
    var dataStatus: RestaurantDataStatusUIState by mutableStateOf(RestaurantDataStatusUIState.Start)
        private set



}