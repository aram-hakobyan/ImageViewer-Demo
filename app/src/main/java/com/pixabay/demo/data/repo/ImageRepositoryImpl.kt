package com.pixabay.demo.data.repo

import com.pixabay.demo.data.api.PixabayApi
import com.pixabay.demo.data.api.mapper.toDomain
import com.pixabay.demo.domain.model.Image
import com.pixabay.demo.domain.repo.ImageRepository
import com.pixabay.demo.domain.repo.TokenRepository
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val api: PixabayApi,
    private val tokenRepository: TokenRepository
) : ImageRepository {

    override suspend fun getImages(
        apiKey: String,
        page: Int
    ): List<Image> {
        return api.getPhotos(
            apiKey = tokenRepository.getToken(),
            page = page
        ).hits.map { entity ->
            entity.toDomain()
        }
    }
}
