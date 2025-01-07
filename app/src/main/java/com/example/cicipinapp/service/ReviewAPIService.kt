package com.example.cicipinapp.service

import com.example.cicipinapp.models.CreateReviewRequest
import com.example.cicipinapp.models.DeleteReviewRequest
import com.example.cicipinapp.models.UpdateReviewRequest
import com.example.cicipinapp.navigation.Screen
import retrofit2.Call
import retrofit2.http.*

interface ReviewAPIService {
    @GET("/reviews")
    fun getAllReviews(): Call<List<Screen.Review>>

    @GET("/reviews/{id}")
    fun getReviewById(@Path("id") id: Int): Call<Screen.Review>

    @POST("/reviews")
    fun createReview(@Body newReview: CreateReviewRequest): Call<String>

    @PUT("/reviews")
    fun updateReview(@Body updatedReview: UpdateReviewRequest): Call<Screen.Review>

    @DELETE("/reviews")
    fun deleteReview(@Body deleteRequest: DeleteReviewRequest): Call<String>
}