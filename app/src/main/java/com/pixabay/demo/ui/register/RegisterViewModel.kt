package com.pixabay.demo.ui.register

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pixabay.demo.data.repo.RegisterRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerRepository: RegisterRepositoryImpl
) : ViewModel() {

    val email = MutableStateFlow("")
    val password = MutableStateFlow("")
    val age = MutableStateFlow("")

    val emailError = MutableStateFlow<String?>(null)
    val passwordError = MutableStateFlow<String?>(null)
    val ageError = MutableStateFlow<String?>(null)

    private val _isRegisterEnabled = MutableStateFlow(false)
    val isRegisterEnabled: StateFlow<Boolean> get() = _isRegisterEnabled

    private val _uiState = MutableStateFlow<RegisterUIState>(RegisterUIState.Idle)
    val uiState: StateFlow<RegisterUIState> get() = _uiState

    private val _isBackClicked = MutableSharedFlow<Boolean>()
    val isBackClicked: SharedFlow<Boolean> get() = _isBackClicked

    sealed interface RegisterUIState {
        data object Idle : RegisterUIState
        data object Error : RegisterUIState
        data object Success : RegisterUIState
    }

    init {
        viewModelScope.launch {
            combine(email, password, age) { email, password, age ->
                validateEmail(email) == null &&
                        validatePassword(password) == null &&
                        validateAge(age) == null
            }.collect { isValid ->
                _isRegisterEnabled.value = isValid
            }
        }
    }

    fun onRegisterClicked() {
        val emailValidation = validateEmail(email.value)
        val passwordValidation = validatePassword(password.value)
        val ageValidation = validateAge(age.value)

        emailError.value = emailValidation
        passwordError.value = passwordValidation
        ageError.value = ageValidation

        if (emailValidation == null && passwordValidation == null && ageValidation == null) {
            viewModelScope.launch {
                val result =
                    registerRepository.register(email.value, password.value, age.value.toInt())
                if (result) {
                    navigateToHome()
                } else {
                    // Handle error, e.g., show Toast
                }
            }
        }
    }

    fun onBackToLoginClicked() {
        navigateToLogin()
    }

    private fun validateEmail(email: String): String? {
        return if (email.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            "Invalid email"
        } else null
    }

    private fun validatePassword(password: String): String? {
        return if (password.length !in 6..12) {
            "Password must be 6-12 characters"
        } else null
    }

    private fun validateAge(age: String): String? {
        val ageValue = age.toIntOrNull()
        return if (ageValue == null || ageValue !in 18..99) {
            "Age must be between 18 and 99"
        } else null
    }

    private fun navigateToHome() {
        _uiState.update {
            RegisterUIState.Success
        }
    }

    private fun navigateToLogin() {
        _isBackClicked.tryEmit(true)
    }
}
