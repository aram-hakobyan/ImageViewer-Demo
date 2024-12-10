package com.pixabay.demo.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pixabay.demo.domain.model.Image
import com.pixabay.demo.domain.usecase.GetImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getImagesUseCase: GetImagesUseCase
) : ViewModel() {

    private val _imageList = MutableStateFlow<List<Image>>(emptyList())
    val imageList: StateFlow<List<Image>> = _imageList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private var currentPage = 1

    var image: Image? = null

    init {
        loadImages()
    }

    fun loadImages() {
        viewModelScope.launch {
            _isLoading.value = true

            val newImages = getImagesUseCase(page = currentPage)
            _imageList.value += newImages

            _isLoading.value = false
            currentPage++
        }
    }
}
