package com.imageviewer.demo.presentation.login

import androidx.lifecycle.ViewModel
import com.imageviewer.demo.framework.ext.launch
import com.imageviewer.demo.framework.ext.logError
import com.imageviewer.demo.framework.util.Validator
import com.imageviewer.demo.domain.model.Result
import com.imageviewer.demo.domain.repository.UserRepository
import com.imageviewer.demo.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val validator: Validator,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUIState>(LoginUIState.Idle)
    val uiState: StateFlow<LoginUIState> = _uiState.asStateFlow()

    private val _isLoginEnabled = MutableStateFlow(false)
    val isLoginEnabled: StateFlow<Boolean> = _isLoginEnabled.asStateFlow()

    private val _isRegisterClicked = MutableSharedFlow<Boolean>()
    val isRegisterClicked: SharedFlow<Boolean> = _isRegisterClicked.asSharedFlow()

    val email = MutableStateFlow("")
    val password = MutableStateFlow("")

    val emailError = MutableStateFlow<String?>(null)
    val passwordError = MutableStateFlow<String?>(null)

    init {
        launch {
            combine(email, password) { email, password ->
                validator.validateEmail(email) == null
                        && validator.validatePassword(password) == null
            }.collect { isValid ->
                _isLoginEnabled.value = isValid
            }
        }
    }

    fun onLoginClicked() {
        val emailValidation = validator.validateEmail(email.value)
        val passwordValidation = validator.validatePassword(password.value)

        emailError.value = emailValidation
        passwordError.value = passwordValidation

        login(emailValidation, passwordValidation)
    }

    private fun login(
        emailValidation: String?,
        passwordValidation: String?
    ) {
        if (emailValidation == null && passwordValidation == null) {
            launch {
                val result = loginUseCase(
                    email.value,
                    password.value
                )
                when (result) {
                    is Result.Success -> onLoginSuccess(token = result.data)
                    is Result.Failure -> logError(result.exception.message)
                }
            }
        }
    }

    private suspend fun onLoginSuccess(token: String) {
        userRepository.saveLoginState(token)

        _uiState.update {
            LoginUIState.Success
        }
    }

    fun onRegisterClicked() {
        launch {
            _isRegisterClicked.emit(true)
        }
    }

    sealed interface LoginUIState {
        data object Idle : LoginUIState
        data object Error : LoginUIState
        data object Success : LoginUIState
    }
}
