package com.imageviewer.demo.presentation.home

import androidx.lifecycle.ViewModel
import com.imageviewer.demo.framework.ext.launch
import com.imageviewer.demo.framework.ext.logError
import com.imageviewer.demo.domain.model.Image
import com.imageviewer.demo.domain.model.Result
import com.imageviewer.demo.domain.repository.UserRepository
import com.imageviewer.demo.domain.usecase.GetImagesUseCase
import com.imageviewer.demo.domain.usecase.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val getImagesUseCase: GetImagesUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _images = MutableStateFlow<List<Image>>(emptyList())
    val images: StateFlow<List<Image>> = _images.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isDetailsVisible = MutableStateFlow(false)
    val isDetailsVisible: StateFlow<Boolean> = _isDetailsVisible.asStateFlow()

    val isSessionExpired: Flow<Boolean> get() = userRepository.isSessionExpired

    private var currentPage = 1

    var selectedImage: Image? = null

    init {
        loadImages()
    }

    fun loadImages() = launch {
        _isLoading.value = true

        when (val result = getImagesUseCase(page = currentPage)) {
            is Result.Success -> {
                _images.value += result.data
            }

            is Result.Failure -> logError(result.exception.message)
        }

        _isLoading.value = false
        ++currentPage
    }

    fun logout() = launch {
        when (val result = logoutUseCase()) {
            is Result.Success -> Unit
            is Result.Failure -> logError(result.exception.message)
        }
    }

    fun toggleDetailsVisibility() {
        _isDetailsVisible.value = !_isDetailsVisible.value
    }

    fun hideDetails() {
        _isDetailsVisible.value = false
    }
}