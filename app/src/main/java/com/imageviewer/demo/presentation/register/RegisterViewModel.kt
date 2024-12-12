package com.imageviewer.demo.presentation.register

import androidx.lifecycle.ViewModel
import com.imageviewer.demo.core.ext.launch
import com.imageviewer.demo.core.ext.logError
import com.imageviewer.demo.core.util.Validator
import com.imageviewer.demo.domain.model.Result
import com.imageviewer.demo.domain.repository.UserRepository
import com.imageviewer.demo.domain.usecase.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val validator: Validator,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<RegisterUIState>(RegisterUIState.Idle)
    val uiState: StateFlow<RegisterUIState> = _uiState.asStateFlow()

    private val _isRegisterEnabled = MutableStateFlow(false)
    val isRegisterEnabled: StateFlow<Boolean> = _isRegisterEnabled.asStateFlow()

    val email = MutableStateFlow("")
    val password = MutableStateFlow("")
    val age = MutableStateFlow("")

    val emailError = MutableStateFlow<String?>(null)
    val passwordError = MutableStateFlow<String?>(null)
    val ageError = MutableStateFlow<String?>(null)

    init {
        launch {
            combine(email, password, age) { email, password, age ->
                validator.validateEmail(email) == null
                        && validator.validatePassword(password) == null
                        && validator.validateAge(age) == null
            }.collect { isValid ->
                _isRegisterEnabled.value = isValid
            }
        }
    }

    fun onRegisterClicked() {
        val emailValidation = validator.validateEmail(email.value)
        val passwordValidation = validator.validatePassword(password.value)
        val ageValidation = validator.validateAge(age.value)

        emailError.value = emailValidation
        passwordError.value = passwordValidation
        ageError.value = ageValidation

        register(emailValidation, passwordValidation, ageValidation)
    }

    private fun register(
        emailValidation: String?,
        passwordValidation: String?,
        ageValidation: String?
    ) {
        if (emailValidation == null
            && passwordValidation == null
            && ageValidation == null
        ) {
            launch {
                val result = registerUseCase(
                    email.value,
                    password.value,
                    age.value.toInt()
                )
                when (result) {
                    is Result.Success -> {
                        userRepository.saveLoginState(result.data)
                        onRegisterSuccess()
                    }

                    is Result.Failure -> logError(result.exception.message)
                }
            }
        }
    }

    private suspend fun onRegisterSuccess() {
        withContext(Dispatchers.Main) {
            _uiState.update {
                RegisterUIState.Success
            }
        }
    }

    fun onBackToLoginClicked() {
        _uiState.update {
            RegisterUIState.Cancelled
        }
    }

    sealed interface RegisterUIState {
        data object Idle : RegisterUIState
        data object Error : RegisterUIState
        data object Success : RegisterUIState
        data object Cancelled : RegisterUIState
    }
}
