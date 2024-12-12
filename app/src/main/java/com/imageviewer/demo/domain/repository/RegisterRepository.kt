package com.imageviewer.demo.domain.repository

import com.imageviewer.demo.domain.model.Result

interface RegisterRepository {

    suspend fun register(
        email: String,
        password: String,
        age: Int
    ): Result<String>
}
