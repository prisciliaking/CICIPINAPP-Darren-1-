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
import com.example.cicipinapp.models.ErrorModel
import com.example.cicipinapp.models.GeneralResponseModel
import com.example.cicipinapp.models.GetRestaurantResponse
import com.example.cicipinapp.models.RestaurantModel
import com.example.cicipinapp.models.RestaurantResponse
import com.example.cicipinapp.models.toRestaurantModel
import com.example.cicipinapp.navigation.Screen
import com.example.cicipinapp.repositories.RestaurantRepository
import com.example.cicipinapp.repositories.UserRepository
import com.example.cicipinapp.uiStates.RestaurantDataStatusUIState
import com.example.cicipinapp.uiStates.StringDataStatusUIState
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class RestaurantViewModel(
    private val restaurantRepository: RestaurantRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    var dataStatus: RestaurantDataStatusUIState by mutableStateOf(RestaurantDataStatusUIState.Start)
        private set

    val token: StateFlow<String> = userRepository.currentUserToken.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ""
    )

    var submissionStatus: StringDataStatusUIState by mutableStateOf(StringDataStatusUIState.Start)
        private set

    var username: StateFlow<String> = userRepository.currentUsername.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ""
    )

    var userID: StateFlow<Int> = userRepository.currentUserID.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0
    )

    var nameInput by mutableStateOf("")
        private set

    var addressInput by mutableStateOf("")
        private set

    var longtitudeInput by mutableStateOf("")
        private set

    var latitudeInput by mutableStateOf("")
        private set

    var descriptionInput by mutableStateOf("")
        private set

    private val _restaurantList = MutableLiveData<List<RestaurantModel>>()
    val restaurantList: LiveData<List<RestaurantModel>> = _restaurantList


    private val _restaurant = MutableStateFlow<RestaurantModel?>(null)
    val restaurant: StateFlow<RestaurantModel?> = _restaurant

    private val _restaurantDetail = MutableStateFlow<GetRestaurantResponse?>(null)
    val restaurantDetail: StateFlow<GetRestaurantResponse?> = _restaurantDetail


    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage


    companion object{
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as CicipInApp)
                val restaurantRepository = application.container.restaurantRepository
                val userRepository = application.container.userRepository
                RestaurantViewModel(restaurantRepository, userRepository)
            }
        }
    }

    fun createRestaurant(
        navController: NavHostController,
        token: String, nameInput: String, addressInput: String, longtitudeInput: String, latitudeInput: String, descriptionInput: String, userIDInput: Int
    ){
        viewModelScope.launch {
            Log.d("token-restaurant", "TOKEN: ${token}")
            try {
                val call = restaurantRepository.createRestaurant(
                    token = token, name = nameInput, address = addressInput, longtitude = longtitudeInput, latitude = latitudeInput, description = descriptionInput, UsersID = userIDInput)

                call.enqueue(object: Callback<GeneralResponseModel> {
                    override fun onResponse(
                        call: Call<GeneralResponseModel>,
                        res: Response<GeneralResponseModel>
                    ) {
                        if (res.isSuccessful) {
                            Log.d("json", "JSON RESPONSE: ${res.body()!!.data}")

                            navController.navigate(Screen.AddMenuList.route) {
                                popUpTo(PagesEnum.MenuList.name) {
                                    inclusive = true
                                }
                            }
                        } else {
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )

                        }
                    }

                    override fun onFailure(call: Call<GeneralResponseModel>, t: Throwable) {
                        submissionStatus = StringDataStatusUIState.Failed(t.localizedMessage)
                    }

                })
            } catch (error: IOException) {
                submissionStatus = StringDataStatusUIState.Failed(error.localizedMessage)
            }
        }
    }

    fun fetchRestaurantById(restaurantId: Int) {
        viewModelScope.launch {
            try {
                val response = restaurantRepository.getRestaurantById(restaurantId).execute()
                if (response.isSuccessful) {
                    response.body()?.let {
                        _restaurant.value = it.toRestaurantModel()
                    }

                } else {
                    Log.e("API_ERROR", "Error fetching details: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("API_FAILURE", "Failed to fetch details: ${e.message}")
            }
        }
    }





    //    PANGGIL RESTAURANT
    fun fetchAllResto() {
        restaurantRepository.getAllRestaurants().enqueue(object : Callback<List<RestaurantModel>> {
            override fun onResponse(
                call: Call<List<RestaurantModel>>,
                response: Response<List<RestaurantModel>>
            ) {
                if (response.isSuccessful) {
                    try {
                        // Get the list of restaurants, or assign an empty list if null
                        val restaurants = response.body() ?: emptyList()

                        if (restaurants.isNotEmpty()) {
                            // Successfully retrieved the list of restaurants
                            Log.d("API_SUCCESS", "Restaurants: $restaurants")
                            _restaurantList.value = restaurants // Update LiveData
                        } else {
                            // No restaurants in the response
                            Log.d("API_SUCCESS", "No restaurants available.")
                            _restaurantList.value = emptyList() // Set an empty list
                        }
                    } catch (e: Exception) {
                        // Handle any unexpected errors while parsing the response
                        Log.e("PARSE_ERROR", "Error parsing response: ${e.message}")
                        _restaurantList.value = emptyList() // Set an empty list on error
                    }
                } else {
                    // Log the error response body for debugging
                    val errorBody = response.errorBody()?.string()
                    Log.e("API_ERROR", "Error response: $errorBody")
                    _restaurantList.value = emptyList() // Set an empty list if error occurs
                }
            }

            override fun onFailure(call: Call<List<RestaurantModel>>, t: Throwable) {
                // Handle network or unexpected errors
                Log.e("API_FAILURE", "Request failed: ${t.message}")
                _restaurantList.value = emptyList() // Set an empty list if request fails
            }
        })
    }




    fun saveUsernameToken(token: String, username: String, userID: Int) {
        viewModelScope.launch {
            userRepository.saveUserToken(token)
            userRepository.saveUsername(username)
            userRepository.saveUserID(userID)
        }
    }

}