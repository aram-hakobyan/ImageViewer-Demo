package com.pixabay.demo.domain.repo

interface TokenRepository {
    suspend fun getToken(): String
}