package com.example.cicipinapp

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.cicipinapp.repositories.*
import com.example.cicipinapp.service.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Interface untuk dependency injection
interface AppContainer {
    val authenticationRepository: AuthenticationRepository
    val userRepository: UserRepository
    val restaurantRepository: RestaurantRepository
    val menuRepository: MenuRepository
    val wishlistRepository: WishlistRepository
    val reviewRepository: ReviewRepository
    val menuCategoryRepository: MenuCategoryRepository
    val restaurantCategoryRepository: RestaurantCategoryRepository
}

// Default implementation
class DefaultAppContainer(
    private val userDataStore: DataStore<Preferences>
) : AppContainer {

    private val baseUrl = "http://192.168.45.47:3000"

    // Retrofit Service
    private val retrofit: Retrofit by lazy {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    // API Services
    private val authenticationAPIService: AuthenticationAPIService by lazy {
        retrofit.create(AuthenticationAPIService::class.java)
    }

    private val userAPIService: UserAPIService by lazy {
        retrofit.create(UserAPIService::class.java)
    }

    private val restaurantAPIService: RestaurantAPIService by lazy {
        retrofit.create(RestaurantAPIService::class.java)
    }

    private val menuAPIService: MenuAPIService by lazy {
        retrofit.create(MenuAPIService::class.java)
    }

    private val wishlistAPIService: WishlistAPIService by lazy {
        retrofit.create(WishlistAPIService::class.java)
    }

    private val reviewAPIService: ReviewAPIService by lazy {
        retrofit.create(ReviewAPIService::class.java)
    }

    private val menuCategoryAPIService: MenuCategoryAPIService by lazy {
        retrofit.create(MenuCategoryAPIService::class.java)
    }

    private val restaurantCategoryAPIService: RestaurantCategoryApi by lazy {
        retrofit.create(RestaurantCategoryApi::class.java)
    }

    // Repositories
    override val authenticationRepository: AuthenticationRepository by lazy {
        NetworkAuthenticationRepository(authenticationAPIService)
    }

    override val userRepository: UserRepository by lazy {
        NetworkUserRepository(userDataStore, userAPIService)
    }

    override val restaurantRepository: RestaurantRepository by lazy {
        NetworkRestaurantRepository(restaurantAPIService)
    }

    override val menuRepository: MenuRepository by lazy {
        NetworkMenuRepository(menuAPIService)
    }

    override val wishlistRepository: WishlistRepository by lazy {
        NetworkWishlistRepository(wishlistAPIService)
    }

    override val reviewRepository: ReviewRepository by lazy {
        NetworkReviewRepository(reviewAPIService)
    }

    override val menuCategoryRepository: MenuCategoryRepository by lazy {
        NetworkMenuCategoryRepository(menuCategoryAPIService)
    }

    override val restaurantCategoryRepository: RestaurantCategoryRepository by lazy {
        RestaurantCategoryRepository(restaurantCategoryAPIService)
    }
}
