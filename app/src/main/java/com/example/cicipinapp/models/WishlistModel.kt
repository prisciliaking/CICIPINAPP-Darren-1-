package com.example.cicipinapp.models

data class BookmarkModel(
    val isBookmarked: Boolean = false,
    val userId: Int = 0,
    val restaurantId: Int = 0
)

data class BookmarkRequest(
    val isBookmarked: Boolean = false,
    val userId: Int = 0,
    val restaurantId: Int = 0
)
//geet the reestaurant isBookmark
data class RestaurantWithWishlistStatusModel(
    val restaurantId: Int,
    val restaurantName: String,
    val isBookmarked: Boolean
)

data class GetAllBookmarks(
    val data: List<BookmarkModel>
)

data class GetBookmarkResponse(
    val data: BookmarkModel
)
