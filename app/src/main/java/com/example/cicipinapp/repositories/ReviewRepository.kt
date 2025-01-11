package com.example.cicipinapp.repositories

import com.example.cicipinapp.models.CreateReviewRequest
import com.example.cicipinapp.models.ReviewModel
import com.example.cicipinapp.models.ReviewResponse
import com.example.cicipinapp.service.ReviewAPIService
import retrofit2.Response

interface ReviewRepository {
    suspend fun createReview(request: CreateReviewRequest): Response<ReviewResponse>
    suspend fun getReviewsForRestaurant(restaurantId: Int): Response<List<ReviewModel>>
    suspend fun updateReview(id: Int, request: CreateReviewRequest): Response<ReviewResponse>
    suspend fun deleteReview(id: Int): Response<Unit>
}

class NetworkReviewRepository(
    private val reviewAPIService: ReviewAPIService
) : ReviewRepository {

    override suspend fun createReview(request: CreateReviewRequest): Response<ReviewResponse> {
        return reviewAPIService.createReview(request)
    }

    override suspend fun getReviewsForRestaurant(restaurantId: Int): Response<List<ReviewModel>> {
        return reviewAPIService.getReviewsForRestaurant(restaurantId)
    }

    override suspend fun updateReview(id: Int, request: CreateReviewRequest): Response<ReviewResponse> {
        return reviewAPIService.updateReview(id, request)
    }

    override suspend fun deleteReview(id: Int): Response<Unit> {
        return reviewAPIService.deleteReview(id)
    }
}
