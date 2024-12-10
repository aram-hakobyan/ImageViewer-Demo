package com.pixabay.demo.data.repo

import com.pixabay.demo.domain.repo.RegisterRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor() : RegisterRepository {

    override suspend fun register(
        email: String,
        password: String,
        age: Int
    ): Boolean {
        delay(1000)
        return email == "example@test.com"
                && password == "Aa123456"
                && age in 18..99
    }
}
