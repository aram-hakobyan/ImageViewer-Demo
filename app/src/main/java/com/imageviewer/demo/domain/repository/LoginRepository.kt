package com.imageviewer.demo.domain.repository

import com.imageviewer.demo.domain.model.Result

interface LoginRepository {

    suspend fun login(
        email: String,
        password: String
    ): Result<String>
}
