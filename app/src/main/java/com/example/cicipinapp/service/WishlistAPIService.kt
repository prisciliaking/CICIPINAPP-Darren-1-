package com.example.cicipinapp.service

import com.example.cicipinapp.models.*
import retrofit2.Call
import retrofit2.http.*

interface WishlistAPIService {

    // Create or Update Wishlist (like or unlike a restaurant)
    @POST("/cicipin/bookmarks")
    fun createOrUpdateWishlist(
        @Header("X-API-TOKEN") token: String,
        @Body wishlistRequest: BookmarkRequest
    ): Call<GeneralResponseModel>

    // Get Wishlist for a User
    @GET("/cicipin/bookmarks")
    fun getWishlist(
        @Header("X-API-TOKEN") token: String,
        @Query("userId") userId: Int
    ): Call<GetAllBookmarks>

    // Get All Restaurants with Wishlist Status for a User
    @GET("/cicipin/restaurants/bookmarks-status")
    fun getRestaurantsWithWishlistStatus(
        @Header("X-API-TOKEN") token: String,
        @Query("userId") userId: Int
    ): Call<List<RestaurantWithWishlistStatusModel>>
}
