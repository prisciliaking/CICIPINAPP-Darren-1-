package com.example.cicipinapp.repositories


import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.cicipinapp.models.GeneralResponseModel
import com.example.cicipinapp.service.UserAPIService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import retrofit2.Call

interface UserRepository {
    val currentUserToken: Flow<String>
    val currentUsername: Flow<String>
    val currentUserID: Flow<Int>

    fun logout(token: String): Call<GeneralResponseModel>

    suspend fun saveUserToken(token: String)

    suspend fun saveUsername(username: String)

    suspend fun saveUserID(userid: Int)
}

class NetworkUserRepository(
    private val userDataStore: DataStore<Preferences>,
    private val userAPIService: UserAPIService
): UserRepository {
    private companion object {
        val USER_TOKEN = stringPreferencesKey("token")
        val USERNAME = stringPreferencesKey("username")
        val USERID = intPreferencesKey("userid")
    }

    override val currentUserToken: Flow<String> = userDataStore.data.map { preferences ->
        preferences[USER_TOKEN] ?: "Unknown"
    }

    override val currentUsername: Flow<String> = userDataStore.data.map { preferences ->
        preferences[USERNAME] ?: "Unknown"
    }

    override val currentUserID: Flow<Int> = userDataStore.data.map { preferences ->
        preferences[USERID] ?: 0
    }

    override suspend fun saveUserToken(token: String) {
        userDataStore.edit { preferences ->
            preferences[USER_TOKEN] = token
        }
    }

    override suspend fun saveUsername(username: String) {
        userDataStore.edit { preferences ->
            preferences[USERNAME] = username
        }
    }

    override suspend fun saveUserID(userid: Int) {
        userDataStore.edit { preferences ->
            preferences[USERID] = userid
        }
    }

    override fun logout(token: String): Call<GeneralResponseModel> {
        return userAPIService.logout(token)
    }
}