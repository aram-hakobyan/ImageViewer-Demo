package com.pixabay.demo.data.api

import com.pixabay.demo.data.entity.PhotoEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotosApi {

    @GET("api/")
    suspend fun getPhotos(
        @Query("key") apiKey: String,
        @Query("page") page: Int
    ): PixabayResponse
}

data class PixabayResponse(
    val hits: List<PhotoEntity>
)