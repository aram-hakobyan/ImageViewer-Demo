package com.imageviewer.demo.domain.usecase

import com.imageviewer.demo.domain.model.Result
import com.imageviewer.demo.domain.model.Image
import com.imageviewer.demo.domain.repository.ImageRepository
import javax.inject.Inject

class GetImagesUseCase @Inject constructor(
    private val repository: ImageRepository
) {
    suspend operator fun invoke(page: Int): Result<List<Image>> {
        return repository.getImages("", page)
    }
}