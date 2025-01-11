package com.example.cicipinapp.service

import com.example.cicipinapp.models.CreateReviewRequest
import com.example.cicipinapp.models.ReviewModel
import com.example.cicipinapp.models.ReviewResponse
import retrofit2.Response
import retrofit2.http.*

interface ReviewAPIService {

    // Endpoint untuk membuat review
    @POST("/cicipin/reviews")
    suspend fun createReview(@Body request: CreateReviewRequest): Response<ReviewResponse>

    // Endpoint untuk mendapatkan reviews berdasarkan restaurant ID
    @GET("/cicipin/restaurants/{restaurantId}/reviews")
    suspend fun getReviewsForRestaurant(@Path("restaurantId") restaurantId: Int): Response<List<ReviewModel>>

    // Endpoint untuk update review
    @PUT("/cicipin/reviews/{id}")
    suspend fun updateReview(@Path("id") id: Int, @Body request: CreateReviewRequest): Response<ReviewResponse>

    // Endpoint untuk menghapus review
    @DELETE("/cicipin/reviews/{id}")
    suspend fun deleteReview(@Path("id") id: Int): Response<Unit>
}