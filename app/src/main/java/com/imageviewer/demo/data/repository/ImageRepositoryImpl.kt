package com.imageviewer.demo.data.repository

import com.imageviewer.demo.data.api.PixabayApi
import com.imageviewer.demo.data.api.mapper.toDomain
import com.imageviewer.demo.domain.model.Result
import com.imageviewer.demo.domain.model.Image
import com.imageviewer.demo.domain.repository.ImageRepository
import com.imageviewer.demo.domain.repository.TokenRepository
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val api: PixabayApi,
    private val tokenRepository: TokenRepository
) : ImageRepository {

    override suspend fun getImages(
        apiKey: String,
        page: Int
    ): Result<List<Image>> {
        return try {
            val apiResult = api.getImages(
                apiKey = tokenRepository.getApiToken(),
                page = page
            ).hits

            Result.Success(
                apiResult.map { entity ->
                    entity.toDomain()
                }
            )
        } catch (exception: Exception) {
            Result.Failure(exception)
        }
    }
}
