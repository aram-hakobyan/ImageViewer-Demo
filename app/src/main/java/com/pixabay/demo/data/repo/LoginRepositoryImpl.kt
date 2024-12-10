package com.pixabay.demo.data.repo

import com.pixabay.demo.domain.repo.LoginRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor() : LoginRepository {

    override suspend fun login(email: String, password: String): Boolean {
        delay(1000)
        return email == "example@test.com" && password == "Aa123456"
    }
}
