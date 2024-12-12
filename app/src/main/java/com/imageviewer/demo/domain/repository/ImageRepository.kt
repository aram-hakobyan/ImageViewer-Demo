package com.imageviewer.demo.domain.repository

import com.imageviewer.demo.domain.model.Result
import com.imageviewer.demo.domain.model.Image

interface ImageRepository {

    suspend fun getImages(
        apiKey: String,
        page: Int
    ): Result<List<Image>>
}