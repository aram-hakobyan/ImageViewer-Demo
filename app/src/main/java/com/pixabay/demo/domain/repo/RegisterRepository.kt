package com.pixabay.demo.domain.repo

interface RegisterRepository {

    suspend fun register(
        email: String,
        password: String,
        age: Int
    ): Boolean
}
