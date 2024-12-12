package com.imageviewer.demo.data.repository

import com.imageviewer.demo.domain.repository.TokenRepository

class TokenRepositoryImpl : TokenRepository {

    override suspend fun getApiToken(): String {
        // TODO: fetch from API service
        return "47545350-033ce61c377726a6b2ac1f5a6"
    }
}