package com.example.cicipinapp.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.cicipinapp.CicipInApp
import com.example.cicipinapp.models.*
import com.example.cicipinapp.repositories.MenuRepository
import com.example.cicipinapp.repositories.RestaurantRepository
import com.example.cicipinapp.uiStates.MenuDataStatusUIState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuViewModel(
    private val menuRepository: MenuRepository,
    private val restaurantRepository: RestaurantRepository,

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
                val application = this[APPLICATION_KEY] as CicipInApp
                val menuRepository = application.container.menuRepository
                val restaurantRepository = application.container.restaurantRepository
                MenuViewModel(menuRepository, restaurantRepository)
            }
        }
    }

    private val _menuDataStatus = MutableLiveData<MenuDataStatusUIState>()
    val menuDataStatus: LiveData<MenuDataStatusUIState> = _menuDataStatus

    fun fetchMenuByRestaurantId(restaurantId: Int) {
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

    //get menu by id
    fun fetchMenuById(id: Int) {

        menuRepository.getMenuById(id).enqueue(object : Callback<GetMenuResponse> {
            override fun onResponse(call: Call<GetMenuResponse>, response: Response<GetMenuResponse>) {
                if (response.isSuccessful) {
                    _menuDetail.value = response.body()?.data
                } else {
                    _errorMessage.value = "Failed to fetch menu: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<GetMenuResponse>, t: Throwable) {
                _errorMessage.value = "Error: ${t.message}"
            }
        })
    }

    // Validate restaurant ID before creating a menu
    fun validateRestaurant(token: String, restaurantId: Int, onSuccess: () -> Unit) {
        restaurantRepository.getRestaurantById(token, restaurantId)
            .enqueue(object : Callback<GetRestaurantResponse> {
                override fun onResponse(
                    call: Call<GetRestaurantResponse>,
                    response: Response<GetRestaurantResponse>
                ) {
                    if (response.isSuccessful) {
                        _restaurantValidation.value = true
                        onSuccess()
                    } else {
                        _restaurantValidation.value = false
                        _errorMessage.value = "Invalid Restaurant ID: ${response.message()}"
                    }
                }

                override fun onFailure(call: Call<GetRestaurantResponse>, t: Throwable) {
                    _restaurantValidation.value = false
                    _errorMessage.value = "Error validating restaurant: ${t.message}"
                }
            })
    }

    // Create a new menu
    fun createMenu(
        token: String,
        name: String,
        image: String,
        description: String,
        price: String,
        restaurantId: Int
    ) {
        validateRestaurant(token, restaurantId) {
            menuRepository.createMenu(token, name, image, description, price, restaurantId)
                .enqueue(object : Callback<GeneralResponseModel> {
                    override fun onResponse(
                        call: Call<GeneralResponseModel>,
                        response: Response<GeneralResponseModel>
                    ) {
                        if (response.isSuccessful) {
                            _createMenuResponse.value = response.body()
                        } else {
                            _errorMessage.value = "Failed to create menu: ${response.message()}"
                        }
                    }

                    override fun onFailure(call: Call<GeneralResponseModel>, t: Throwable) {
                        _errorMessage.value = "Error: ${t.message}"
                    }
                })
        }
    }
}
