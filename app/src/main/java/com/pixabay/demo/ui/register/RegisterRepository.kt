package com.pixabay.demo.ui.register

import kotlinx.coroutines.delay
import javax.inject.Inject

class RegisterRepository @Inject constructor() {

    suspend fun register(
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
