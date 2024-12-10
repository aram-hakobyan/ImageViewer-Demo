package com.pixabay.demo.ui.login

import kotlinx.coroutines.delay
import javax.inject.Inject

class LoginRepository @Inject constructor() {

    suspend fun login(email: String, password: String): Boolean {
        delay(1000)
        return email == "example@test.com" && password == "Aa123456"
    }
}
