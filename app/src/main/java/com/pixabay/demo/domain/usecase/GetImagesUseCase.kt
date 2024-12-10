package com.pixabay.demo.domain.usecase

import com.pixabay.demo.domain.model.Image
import com.pixabay.demo.domain.repo.ImageRepository
import javax.inject.Inject

class GetImagesUseCase @Inject constructor(
    private val repository: ImageRepository
) {
    suspend operator fun invoke(page: Int): List<Image> {
        return repository.getImages("", page)
    }
}