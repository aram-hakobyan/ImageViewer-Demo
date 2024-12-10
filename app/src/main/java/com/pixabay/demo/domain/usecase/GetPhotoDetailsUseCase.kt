package com.pixabay.demo.domain.usecase

import com.pixabay.demo.domain.model.Photo
import com.pixabay.demo.domain.repo.PhotosRepository
import javax.inject.Inject

class GetPhotoDetailsUseCase @Inject constructor(
    private val repository: PhotosRepository
) {
    suspend operator fun invoke(id: String): Photo? {
        return repository.getPhoto(id)
    }
}