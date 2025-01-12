package com.example.cicipinapp.viewModels

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.cicipinapp.repositories.AuthenticationRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cicipinapp.uiStates.AuthenticationStatusUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.cicipinapp.CicipInApp
import com.example.cicipinapp.enums.PagesEnum
import com.example.cicipinapp.models.ErrorModel
import com.example.cicipinapp.models.GeneralResponseModel
import com.example.cicipinapp.models.UserResponse
import com.example.cicipinapp.repositories.UserRepository
import com.example.cicipinapp.uiStates.StringDataStatusUIState
import com.google.gson.Gson
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class AuthenticationViewModel(
    private val authenticationRepository: AuthenticationRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    var logoutStatus: StringDataStatusUIState by mutableStateOf(StringDataStatusUIState.Start)
        private set

    var dataStatus: AuthenticationStatusUIState by mutableStateOf(AuthenticationStatusUIState.Start)
        private set

    var profilePictureInput by mutableStateOf("lmaflkfsalklaslas")
        private set
    var nameInput by mutableStateOf("")
        private set
    var usernameInput by mutableStateOf("")
        private set
    var emailInput by mutableStateOf("")
        private set
    var passwordInput by mutableStateOf("")
        private set
    var roleInput by mutableStateOf("user")
        private set
    var passwordVisible by mutableStateOf(false)
        private set




    fun registerUser(navController: NavHostController, profilePictureInput: String, nameInput: String, usernameInput: String, emailInput: String, passwordInput: String, roleInput: String){
        viewModelScope.launch{
            dataStatus = AuthenticationStatusUIState.Loading

            try{
                val call = authenticationRepository.register(profilePictureInput, nameInput, usernameInput, emailInput, passwordInput, roleInput)

                call.enqueue(object : Callback<UserResponse>{
                    override fun onResponse(
                        call: Call<UserResponse>,
                        response: Response<UserResponse>
                    ) {
                        if (response.isSuccessful) {
                            Log.d("response-data", "RESPONSE DATA: ${response.body()}")
//                            saveUsernameToken(response.body()!!.data.token!!, response.body()!!.data.profile_picture, response.body()!!.data.name, response.body()!!.data.username, response.body()!!.data.email, response.body()!!.data.role)
                            dataStatus = AuthenticationStatusUIState.Success(response.body()!!.data)


                            navController.navigate(PagesEnum.Login.name) {
                                popUpTo(PagesEnum.Home.name) {
                                    inclusive = true
                                }
                            }
                        } else
                        {
                            val errorMessage = Gson().fromJson(
                                response.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )

                            Log.d("error-data", "ERROR DATA: ${errorMessage}")
                            dataStatus = AuthenticationStatusUIState.Failed(errorMessage.errors)
                        }
                    }

                    override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                        Log.d("error-data", "ERROR DATA: ${t.localizedMessage}")
                        dataStatus = AuthenticationStatusUIState.Failed(t.localizedMessage)
                    }
                })
            }catch(error: IOException) {
                dataStatus = AuthenticationStatusUIState.Failed(error.localizedMessage)
                Log.d("register-error", "REGISTER ERROR: ${error.localizedMessage}")
            }

        }

    }
    fun loginUser(
        navController: NavHostController,
        usernameInput: String,
        passwordInput: String
    ) {
        viewModelScope.launch {
            dataStatus = AuthenticationStatusUIState.Loading
            try {
                val call = authenticationRepository.login(usernameInput, passwordInput)
                call.enqueue(object: Callback<UserResponse> {
                    override fun onResponse(call: Call<UserResponse>, res: Response<UserResponse>) {
                        if (res.isSuccessful) {
                            saveUsernameToken(res.body()!!.data.token!!, res.body()!!.data.username, res.body()!!.data.id)

                            dataStatus = AuthenticationStatusUIState.Success(res.body()!!.data)

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

                            Log.d("error-data", "ERROR DATA: ${errorMessage.errors}")
                            dataStatus = AuthenticationStatusUIState.Failed(errorMessage.errors)
                        }
                    }

                    override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                        dataStatus = AuthenticationStatusUIState.Failed(t.localizedMessage)
                    }

                })
            } catch (error: IOException) {
                dataStatus = AuthenticationStatusUIState.Failed(error.localizedMessage)
                Log.d("register-error", "LOGIN ERROR: ${error.toString()}")
            }
        }
    }

//    fun logoutUser(token: String, navController: NavHostController) {
//        viewModelScope.launch {
//            logoutStatus = StringDataStatusUIState.Loading
//
//            Log.d("token-logout", "LOGOUT TOKEN: ${token}")
//
//            try {
//                val call = userRepository.logout(token)
//
//                call.enqueue(object: Callback<GeneralResponseModel>{
//                    override fun onResponse(call: Call<GeneralResponseModel>, res: Response<GeneralResponseModel>) {
//                        if (res.isSuccessful) {
//                            logoutStatus = StringDataStatusUIState.Success(data = res.body()!!.data)
//
//                            saveUsernameToken("Unknown", "Unknown", 0)
//
//                            navController.navigate(PagesEnum.Login.name) {
//                                popUpTo(PagesEnum.Home.name) {
//                                    inclusive = true
//                                }
//                            }
//                        } else {
//                            val errorMessage = Gson().fromJson(
//                                res.errorBody()!!.charStream(),
//                                ErrorModel::class.java
//                            )
//
//                            logoutStatus = StringDataStatusUIState.Failed(errorMessage.errors)
//                            // set error message toast
//                        }
//                    }
//
//                    override fun onFailure(call: Call<GeneralResponseModel>, t: Throwable) {
//                        logoutStatus = StringDataStatusUIState.Failed(t.localizedMessage)
//                        Log.d("logout-failure", t.localizedMessage)
//                    }
//                })
//            } catch (error: IOException) {
//                logoutStatus = StringDataStatusUIState.Failed(error.localizedMessage)
//                Log.d("logout-error", error.localizedMessage)
//            }
//        }
//    }

    fun logout(userid: Int, onSuccess: (String) -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val response = userRepository.logout(userid).execute() // Synchronous call
                if (response.isSuccessful && response.body() != null) {
                    userRepository.clearUserSession() // Clear local session
                    onSuccess(response.body()!!.data)
                } else {
                    onError(response.errorBody()?.string() ?: "Unknown error")
                }
            } catch (e: Exception) {
                onError(e.message ?: "An error occurred")
            }
        }
    }



    fun saveUsernameToken(token: String, username: String, userID: Int) {
        viewModelScope.launch {
            userRepository.saveUserToken(token)
            userRepository.saveUsername(username)
            userRepository.saveUserID(userID)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as CicipInApp)
                val authenticationRepository = application.container.authenticationRepository
                val userRepository = application.container.userRepository
                AuthenticationViewModel(authenticationRepository, userRepository)
            }
        }
    }
    fun clearErrorMessage() {
        dataStatus = AuthenticationStatusUIState.Start
    }

    fun clearLogoutErrorMessage() {
        logoutStatus = StringDataStatusUIState.Start
    }
}