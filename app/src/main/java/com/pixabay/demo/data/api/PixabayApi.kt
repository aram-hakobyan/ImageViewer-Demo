package com.pixabay.demo.data.api

import com.pixabay.demo.data.api.response.ResponseEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayApi {

    @GET("api/")
    suspend fun getPhotos(
        @Query("key") apiKey: String,
        @Query("page") page: Int
    ): ResponseEntity
}
