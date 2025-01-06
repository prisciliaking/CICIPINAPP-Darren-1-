package com.example.cicipinapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cicipinapp.models.*
import com.example.cicipinapp.repositories.WishlistRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WishlistViewModel(
    private val wishlistRepository: WishlistRepository
) : ViewModel() {

    private val _wishlist = MutableLiveData<List<BookmarkModel>>()
    val wishlist: LiveData<List<BookmarkModel>> = _wishlist

    private val _restaurantWishlistStatus = MutableLiveData<List<RestaurantWithWishlistStatusModel>>()
    val restaurantWishlistStatus: LiveData<List<RestaurantWithWishlistStatusModel>> = _restaurantWishlistStatus

    private val _operationResult = MutableLiveData<GeneralResponseModel>()
    val operationResult: LiveData<GeneralResponseModel> = _operationResult

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    // Fetch the wishlist for a user
    fun fetchWishlist(token: String, userId: Int) {
        wishlistRepository.getWishlist(token, userId).enqueue(object : Callback<GetAllBookmarks> {
            override fun onResponse(call: Call<GetAllBookmarks>, response: Response<GetAllBookmarks>) {
                if (response.isSuccessful) {
                    _wishlist.value = response.body()?.data ?: emptyList()
                } else {
                    _errorMessage.value = "Failed to fetch wishlist: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<GetAllBookmarks>, t: Throwable) {
                _errorMessage.value = "Error: ${t.message}"
            }
        })
    }

    // Fetch all restaurants with wishlist status for a user
    fun fetchRestaurantsWithWishlistStatus(token: String, userId: Int) {
        wishlistRepository.getRestaurantsWithWishlistStatus(token, userId)
            .enqueue(object : Callback<List<RestaurantWithWishlistStatusModel>> {
                override fun onResponse(
                    call: Call<List<RestaurantWithWishlistStatusModel>>,
                    response: Response<List<RestaurantWithWishlistStatusModel>>
                ) {
                    if (response.isSuccessful) {
                        _restaurantWishlistStatus.value = response.body() ?: emptyList()
                    } else {
                        _errorMessage.value = "Failed to fetch restaurant wishlist status: ${response.message()}"
                    }
                }

                override fun onFailure(call: Call<List<RestaurantWithWishlistStatusModel>>, t: Throwable) {
                    _errorMessage.value = "Error: ${t.message}"
                }
            })
    }

    // Add or remove a restaurant from the wishlist
    fun updateWishlist(token: String, userId: Int, restaurantId: Int, isBookmarked: Boolean) {
        val request = BookmarkRequest(
            isBookmarked = isBookmarked,
            userId = userId,
            restaurantId = restaurantId
        )

        wishlistRepository.createOrUpdateWishlist(token, request).enqueue(object : Callback<GeneralResponseModel> {
            override fun onResponse(call: Call<GeneralResponseModel>, response: Response<GeneralResponseModel>) {
                if (response.isSuccessful) {
                    _operationResult.value = response.body()
                    fetchWishlist(token, userId) // Refresh wishlist after update
                } else {
                    _errorMessage.value = "Failed to update wishlist: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<GeneralResponseModel>, t: Throwable) {
                _errorMessage.value = "Error: ${t.message}"
            }
        })
    }
}
