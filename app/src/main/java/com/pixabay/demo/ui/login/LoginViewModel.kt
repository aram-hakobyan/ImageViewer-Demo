package com.pixabay.demo.ui.login

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pixabay.demo.data.repo.LoginRepositoryImpl
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
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepositoryImpl
) : ViewModel() {

    val email = MutableStateFlow("")
    val password = MutableStateFlow("")

    val emailError = MutableStateFlow<String?>(null)
    val passwordError = MutableStateFlow<String?>(null)

    private val _isLoginEnabled = MutableStateFlow(false)
    val isLoginEnabled: StateFlow<Boolean> get() = _isLoginEnabled

    private val _isRegisterClicked = MutableSharedFlow<Boolean>()
    val isRegisterClicked: SharedFlow<Boolean> get() = _isRegisterClicked

    private val _uiState = MutableStateFlow<LoginUIState>(LoginUIState.Idle)
    val uiState: StateFlow<LoginUIState> get() = _uiState

    sealed interface LoginUIState {
        data object Idle : LoginUIState
        data object Error : LoginUIState
        data object Success : LoginUIState
    }

    init {
        viewModelScope.launch {
            combine(email, password) { email, password ->
                validateEmail(email) == null && validatePassword(password) == null
            }.collect { isValid ->
                _isLoginEnabled.value = isValid
            }
        }
    }

    fun onLoginClicked() {
        val emailValidation = validateEmail(email.value)
        val passwordValidation = validatePassword(password.value)

        emailError.value = emailValidation
        passwordError.value = passwordValidation

        if (emailValidation == null && passwordValidation == null) {
            viewModelScope.launch {
                val result = loginRepository.login(email.value, password.value)
                if (result) {
                    navigateToHome()
                } else {
                    // Handle error, e.g., show Toast
                }
            }
        }
    }

    fun onRegisterClicked() {
        navigateToRegister()
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

    private fun navigateToHome() {
        _uiState.update {
            LoginUIState.Success
        }
    }

    private fun navigateToRegister() {
        _isRegisterClicked.tryEmit(true)
    }
}
