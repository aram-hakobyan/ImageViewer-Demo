package com.imageviewer.demo.framework.util

import android.content.Context
import android.util.Patterns
import com.imageviewer.demo.R
import javax.inject.Inject

class Validator @Inject constructor(
    private val context: Context
) {

    fun validateEmail(email: String): String? {
        return if (email.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            context.getString(R.string.invalid_email)
        } else null
    }

    fun validatePassword(password: String): String? {
        return if (password.length !in MIN_PASSWORD_LENGTH..MAX_PASSWORD_LENGTH) {
            context.getString(
                R.string.password_must_be_in_range,
                MIN_PASSWORD_LENGTH,
                MAX_PASSWORD_LENGTH
            )
        } else null
    }

    fun validateAge(age: String): String? {
        val ageValue = age.toIntOrNull()
        return if (ageValue == null || ageValue !in MIN_AGE_REQUIRED..MAX_AGE_REQUIRED) {
            context.getString(
                R.string.age_must_be_in_range,
                MIN_AGE_REQUIRED,
                MAX_AGE_REQUIRED
            )
        } else null
    }
}

private const val MIN_PASSWORD_LENGTH = 6
private const val MAX_PASSWORD_LENGTH = 12
private const val MIN_AGE_REQUIRED = 18
private const val MAX_AGE_REQUIRED = 99