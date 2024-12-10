package com.pixabay.demo.domain.repo

import com.pixabay.demo.domain.model.Photo

interface PhotosRepository {

    suspend fun getPhotos(
        apiKey: String,
        page: Int
    ): List<Photo>

    suspend fun getPhoto(
        id: String
    ): Photo?

}