package com.imageviewer.demo.domain.repository

import com.imageviewer.demo.domain.model.Result
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    val isSessionExpired: Flow<Boolean>

    suspend fun saveLoginState(
        authToken: String
    )

    suspend fun logout(): Result<Unit>
}
