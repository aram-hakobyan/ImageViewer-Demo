package com.imageviewer.demo.domain.usecase

import com.imageviewer.demo.domain.model.Result
import com.imageviewer.demo.domain.repository.UserRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(
    ): Result<Unit> {
        return repository.logout()
    }
}