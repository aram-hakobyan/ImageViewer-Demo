package com.imageviewer.demo.domain.repository

interface TokenRepository {

    suspend fun getApiToken(): String
}