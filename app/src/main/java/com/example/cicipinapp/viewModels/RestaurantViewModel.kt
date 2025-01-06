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

    fun updateNameInput(input: String) {
        nameInput = input
    }

    fun updateAddressInput(input: String) {
        addressInput = input
    }

    fun updateLongtitudeInput(input: String) {
        longtitudeInput = input
    }

    fun updateLatitudeInput(input: String) {
        latitudeInput = input
    }

    fun updateDescriptionInput(input: String) {
        descriptionInput = input
    }

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

                            navController.navigate(PagesEnum.Home.name) {
                                popUpTo(PagesEnum.Login.name) {
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
                        submissionStatus = StringDataStatusUIState.Failed(t.localizedMessage ?: "An unknown error occurred")
                    }


                })
            } catch (error: IOException) {
                dataStatus = RestaurantDataStatusUIState.Failed(error.localizedMessage ?: "An unknown error occurred")
            }

        }
    }


//    PANGGIL RESTAURANT
fun getAllRestaurants(token: String) {
    viewModelScope.launch {
        Log.d("token-home", "TOKEN AT HOME: ${token}")
        try {
            val call = restaurantRepository.getAllRestaurants(token)
            call.enqueue(object : Callback<GetAllRestaurant> {
                override fun onResponse(call: Call<GetAllRestaurant>, res: Response<GetAllRestaurant>) {
                    if (res.isSuccessful) {
                        dataStatus = RestaurantDataStatusUIState.Success(res.body()!!.data)

                        Log.d("data-result", "TODO LIST DATA: ${dataStatus}")
                    } else {
                        val errorMessage = Gson().fromJson(
                            res.errorBody()!!.charStream(),
                            ErrorModel::class.java
                        )

                        dataStatus = RestaurantDataStatusUIState.Failed(errorMessage.errors)
                    }
                }

                override fun onFailure(call: Call<GetAllRestaurant>, t: Throwable) {
                    dataStatus = RestaurantDataStatusUIState.Failed(t.localizedMessage ?: "An unknown error occurred")
                }

            })
        } catch (error: IOException) {
            dataStatus = RestaurantDataStatusUIState.Failed(error.localizedMessage ?: "An unknown error occurred")
        }
    }
}

}