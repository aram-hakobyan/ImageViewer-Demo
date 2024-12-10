package com.pixabay.demo.data.repo

import com.pixabay.demo.data.api.PhotosApi
import com.pixabay.demo.data.entity.toDomain
import com.pixabay.demo.domain.model.Photo
import com.pixabay.demo.domain.repo.PhotosRepository
import javax.inject.Inject

class PhotosRepositoryImpl @Inject constructor(
    private val api: PhotosApi
) : PhotosRepository {

    override suspend fun getPhotos(
        apiKey: String,
        page: Int
    ): List<Photo> {
        return api.getPhotos(
            apiKey = "47545350-033ce61c377726a6b2ac1f5a6",
            page = page
        ).hits.map { entity ->
            entity.toDomain()
        }
    }

    override suspend fun getPhoto(id: String): Photo? {
        // TODO: read from cache
        return null
    }
}
