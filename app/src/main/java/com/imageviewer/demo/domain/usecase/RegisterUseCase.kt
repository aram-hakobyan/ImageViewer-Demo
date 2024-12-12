package com.imageviewer.demo.domain.usecase

import com.imageviewer.demo.domain.model.Result
import com.imageviewer.demo.domain.repository.RegisterRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: RegisterRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String,
        age: Int
    ): Result<String> {
        return repository.register(email, password, age)
    }
}