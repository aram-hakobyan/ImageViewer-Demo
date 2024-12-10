package com.pixabay.demo.domain.repo

interface LoginRepository {

    suspend fun login(
        email: String,
        password: String
    ): Boolean
}
