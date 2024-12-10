package com.pixabay.demo.domain.usecase

import com.pixabay.demo.domain.model.Photo
import com.pixabay.demo.domain.repo.PhotosRepository
import javax.inject.Inject

class GetPhotosUseCase @Inject constructor(
    private val repository: PhotosRepository
) {
    suspend operator fun invoke(page: Int): List<Photo> {
        return repository.getPhotos("", page)
    }
}