package com.example.cicipinapp.repositories

import com.example.cicipinapp.models.*
import com.example.cicipinapp.service.WishlistAPIService
import retrofit2.Call

interface WishlistRepository {
    fun createOrUpdateWishlist(token: String, request: BookmarkRequest): Call<GeneralResponseModel>
    fun getWishlist(token: String, userId: Int): Call<GetAllBookmarks>
    fun getRestaurantsWithWishlistStatus(token: String, userId: Int): Call<List<RestaurantWithWishlistStatusModel>>
}

class NetworkWishlistRepository(
    private val wishlistAPIService: WishlistAPIService
) : WishlistRepository {
    override fun createOrUpdateWishlist(
        token: String,
        request: BookmarkRequest
    ): Call<GeneralResponseModel> {
        return wishlistAPIService.createOrUpdateWishlist(token, request)
    }

    override fun getWishlist(token: String, userId: Int): Call<GetAllBookmarks> {
        return wishlistAPIService.getWishlist(token, userId)
    }

    override fun getRestaurantsWithWishlistStatus(
        token: String,
        userId: Int
    ): Call<List<RestaurantWithWishlistStatusModel>> {
        return wishlistAPIService.getRestaurantsWithWishlistStatus(token, userId)
    }
}
