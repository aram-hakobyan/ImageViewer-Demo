package com.imageviewer.demo.data.repository

import com.imageviewer.demo.domain.model.Result
import com.imageviewer.demo.domain.repository.RegisterRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor() : RegisterRepository {

    override suspend fun register(
        email: String,
        password: String,
        age: Int
    ): Result<String> {
        // TODO: Change to real API call
        delay(1000)

        return try {
            Result.Success("put_auth_token_here")
        } catch (exception: Exception) {
            Result.Failure(exception)
        }
    }
}
