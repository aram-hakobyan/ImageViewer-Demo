package com.pixabay.demo.domain.repo

import com.pixabay.demo.domain.model.Image

interface ImageRepository {

    suspend fun getImages(
        apiKey: String,
        page: Int
    ): List<Image>
}