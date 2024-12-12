package com.imageviewer.demo.data.repository

import com.imageviewer.demo.domain.model.Result
import com.imageviewer.demo.domain.repository.LoginRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor() : LoginRepository {

    override suspend fun login(email: String, password: String): Result<String> {
        // TODO: Change to real API call
        delay(1000)

        return try {
            Result.Success("put_auth_token_here")
        } catch (exception: Exception) {
            Result.Failure(exception)
        }
    }
}
