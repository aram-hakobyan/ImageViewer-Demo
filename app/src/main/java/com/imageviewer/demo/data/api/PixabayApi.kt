package com.imageviewer.demo.data.api

import com.imageviewer.demo.data.api.response.HitsEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayApi {

    @GET("api/")
    suspend fun getImages(
        @Query("key") apiKey: String,
        @Query("page") page: Int
    ): HitsEntity
}
