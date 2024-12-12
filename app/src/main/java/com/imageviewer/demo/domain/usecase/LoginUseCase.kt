package com.imageviewer.demo.domain.usecase

import com.imageviewer.demo.domain.model.Result
import com.imageviewer.demo.domain.repository.LoginRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: LoginRepository
) {
    suspend operator fun invoke(
        email: String, password: String
    ): Result<String> {
        return repository.login(email, password)
    }
}