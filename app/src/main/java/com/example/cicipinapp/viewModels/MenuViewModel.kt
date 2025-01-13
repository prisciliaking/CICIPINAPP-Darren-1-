// File: MenuViewModel.kt
package com.example.cicipinapp.viewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavHostController
import com.example.cicipinapp.CicipInApp
import com.example.cicipinapp.enums.PagesEnum
import com.example.cicipinapp.models.*
import com.example.cicipinapp.navigation.Screen
import com.example.cicipinapp.repositories.MenuRepository
import com.example.cicipinapp.repositories.RestaurantRepository
import com.example.cicipinapp.uiStates.MenuDataStatusUIState
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class MenuViewModel(
    private val menuRepository: MenuRepository,
    private val restaurantRepository: RestaurantRepository
) : ViewModel() {

    private val _menuList = MutableLiveData<List<MenuModel>>()
    val menuList: LiveData<List<MenuModel>> = _menuList

    private val _menuDetail = MutableLiveData<MenuModel>()
    val menuDetail: LiveData<MenuModel> = _menuDetail

    private val _createMenuResponse = MutableLiveData<GeneralResponseModel>()
    val createMenuResponse: LiveData<GeneralResponseModel> = _createMenuResponse

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _restaurantValidation = MutableLiveData<Boolean>()
    val restaurantValidation: LiveData<Boolean> = _restaurantValidation

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                // Access the Application to get the AppContainer
                val application = this[APPLICATION_KEY] as CicipInApp
                val menuRepository = application.container.menuRepository
                val restaurantRepository = application.container.restaurantRepository
                MenuViewModel(menuRepository, restaurantRepository)
            }
        }
    }

    var menuName by mutableStateOf("")
        private set
    var menuDescription by mutableStateOf("")
        private set
    var menuPrice by mutableStateOf("")
        private set
    var RestaurantsID = MutableStateFlow(6)
        private set
    var imageUrl by mutableStateOf("")

    private val _menuDataStatus = MutableLiveData<MenuDataStatusUIState>()
    val menuDataStatus: LiveData<MenuDataStatusUIState> = _menuDataStatus

    // Create a new menu
    fun createMenu(
        navController: NavHostController,
        token: String,
        menuName: String,
        imageUrl: String,
        menuDescription: String,
        menuPrice: String,
        RestaurantsID: Int
    ) {
        viewModelScope.launch {
            Log.d("token-menu", "TOKEN: $token")
            try {
                val call = menuRepository.createMenu(
                    token = token,
                    name = menuName,
                    image = imageUrl,
                    description = menuDescription,
                    price = menuPrice,
                    restaurantsID  = RestaurantsID
                )

                call.enqueue(object : Callback<GeneralResponseModel> {
                    override fun onResponse(
                        call: Call<GeneralResponseModel>,
                        response: Response<GeneralResponseModel>
                    ) {
                        if (response.isSuccessful) {
                            Log.d("json", "JSON RESPONSE: ${response.body()!!.data}")

                            navController.navigate(Screen.Setting.route) {
                                popUpTo(PagesEnum.MenuList.name) {
                                    inclusive = true
                                }
                            }
                        } else {
                            val errorMessage = Gson().fromJson(
                                response.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )
                        }
                    }

                    override fun onFailure(call: Call<GeneralResponseModel>, t: Throwable) {
                        Log.e("createMenu", "Error: ${t.localizedMessage}")
                    }
                })
            } catch (error: IOException) {
                Log.e("createMenu", "Error: ${error.localizedMessage}")
            }
        }
    }

    fun     fetchMenuByRestaurantId(restaurantId: Int) {
        _menuDataStatus.value = MenuDataStatusUIState.Loading

        menuRepository.getMenuByRestaurantId(restaurantId).enqueue(object : Callback<GetAllMenu> {
            override fun onResponse(call: Call<GetAllMenu>, response: Response<GetAllMenu>) {
                if (response.isSuccessful) {
                    val menuList = response.body() ?: emptyList()
                    if (menuList.isEmpty()) {
                        _menuDataStatus.value = MenuDataStatusUIState.Empty
                    } else {
                        _menuDataStatus.value = MenuDataStatusUIState.Success(menuList)
                    }
                } else {
                    _menuDataStatus.value = MenuDataStatusUIState.Error(
                        response.message() ?: "Failed to fetch menus."
                    )
                }
            }

            override fun onFailure(call: Call<GetAllMenu>, t: Throwable) {
                _menuDataStatus.value = MenuDataStatusUIState.Error(
                    t.localizedMessage ?: "Unknown error occurred."
                )
            }
        })
    }

//    fun fetchMenuById(id: Int) {
//        Log.d("Menu View Model", "Fetch menu by id : $id")
//        // Set the UI state to Loading before making the network call
//        _menuDataStatus.value = MenuDataStatusUIState.Loading
//
//        menuRepository.getMenuById(id).enqueue(object : Callback<GetMenuResponse> {
//
//            override fun onResponse(call: Call<GetMenuResponse>, response: Response<GetMenuResponse>) {
//                if (response.isSuccessful) {
//                    val menu = response.body()?.data
//                    if (menu != null) {
//                        // Wrap the single MenuModel in a list and update UI state to Success
//                        _menuDataStatus.value = MenuDataStatusUIState.Success(listOf(menu))
//                    } else {
//                        // If menu is null, update UI state to Empty
//                        _menuDataStatus.value = MenuDataStatusUIState.Empty
//                    }
//                } else {
//                    // If response is not successful, update UI state to Error with message
//                    _menuDataStatus.value = MenuDataStatusUIState.Error(
//                        "Failed to fetch menu: ${response.message()}"
//                    )
//                }
//            }
//
//            override fun onFailure(call: Call<GetMenuResponse>, t: Throwable) {
//                // If the network call fails, update UI state to Error with throwable message
//                _menuDataStatus.value = MenuDataStatusUIState.Error(
//                    "Error: ${t.message}"
//                )
//            }
//        })
//    }
//

}