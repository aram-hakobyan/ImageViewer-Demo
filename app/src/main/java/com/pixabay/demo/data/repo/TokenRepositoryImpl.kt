package com.pixabay.demo.data.repo

import com.pixabay.demo.domain.repo.TokenRepository

class TokenRepositoryImpl : TokenRepository {

    override suspend fun getToken(): String {
        // TODO: fetch from a backend service
        return "47545350-033ce61c377726a6b2ac1f5a6"
    }
}