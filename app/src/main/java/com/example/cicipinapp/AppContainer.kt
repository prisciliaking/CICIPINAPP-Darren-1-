package com.example.cicipinapp

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.cicipinapp.repositories.AuthenticationRepository
import com.example.cicipinapp.repositories.MenuRepository
import com.example.cicipinapp.repositories.NetworkAuthenticationRepository
import com.example.cicipinapp.repositories.NetworkMenuRepository
import com.example.cicipinapp.repositories.NetworkRestaurantRepository
import com.example.cicipinapp.repositories.NetworkUserRepository
import com.example.cicipinapp.repositories.NetworkWishlistRepository
import com.example.cicipinapp.repositories.RestaurantRepository
import com.example.cicipinapp.repositories.UserRepository
import com.example.cicipinapp.repositories.WishlistRepository
import com.example.cicipinapp.service.AuthenticationAPIService
import com.example.cicipinapp.service.MenuAPIService
import com.example.cicipinapp.service.RestaurantAPIService
import com.example.cicipinapp.service.UserAPIService
import com.example.cicipinapp.service.WishlistAPIService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


// A container is an object that contains the dependencies that the app requires.
// These dependencies are used across the whole application, so they need to be in a common place that all activities can use.
// You can create a subclass of the Application class and store a reference to the container.
interface AppContainer {
    val authenticationRepository: AuthenticationRepository
    val userRepository: UserRepository
    val restaurantRepository: RestaurantRepository
    val menuRepository: MenuRepository
    val wishlistRepository: WishlistRepository
}

class DefaultAppContainer(
    private val userDataStore: DataStore<Preferences>
) : AppContainer {
    // change it to your own local ip please
    private val baseUrl = "http:/192.168.0.100:3000"

    // RETROFIT SERVICE
    // delay object creation until needed using lazy
    private val authenticationRetrofitService: AuthenticationAPIService by lazy {
        val retrofit = initRetrofit()

        retrofit.create(AuthenticationAPIService::class.java)
    }

    private val userRetrofitService: UserAPIService by lazy {
        val retrofit = initRetrofit()

        retrofit.create(UserAPIService::class.java)
    }

    private val restaurantRetrofitService: RestaurantAPIService by lazy {
        val retrofit = initRetrofit()

        retrofit.create(RestaurantAPIService::class.java)
    }

    private val menuRetrofitService: MenuAPIService by lazy {
        val retrofit = initRetrofit()

        retrofit.create(MenuAPIService::class.java)
    }

    private val wishlistRetrofitService: WishlistAPIService by lazy {
        val retrofit = initRetrofit()

        retrofit.create(WishlistAPIService::class.java)
    }


    // REPOSITORY INIT
    // Passing in the required objects is called dependency injection (DI). It is also known as inversion of control.
    override val authenticationRepository: AuthenticationRepository by lazy {
        NetworkAuthenticationRepository(authenticationRetrofitService)
    }

    override val userRepository: UserRepository by lazy {
        NetworkUserRepository(userDataStore, userRetrofitService)
    }

    override val restaurantRepository: RestaurantRepository by lazy {
        NetworkRestaurantRepository(restaurantRetrofitService)
    }

    override val menuRepository: MenuRepository by lazy {
        NetworkMenuRepository(menuRetrofitService)
    }

    override val wishlistRepository: WishlistRepository by lazy {
        NetworkWishlistRepository(wishlistRetrofitService)
    }

    private fun initRetrofit(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.level = (HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
        client.addInterceptor(logging)

        return Retrofit
            .Builder()
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .client(client.build())
            .baseUrl(baseUrl)
            .build()
    }
}