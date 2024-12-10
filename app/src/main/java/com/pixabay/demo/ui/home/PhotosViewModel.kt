package com.pixabay.demo.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pixabay.demo.domain.model.Photo
import com.pixabay.demo.domain.usecase.GetPhotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotosViewModel @Inject constructor(
    private val getPhotosUseCase: GetPhotosUseCase
) : ViewModel() {

    private val _imageList = MutableStateFlow<List<Photo>>(emptyList())
    val imageList: StateFlow<List<Photo>> = _imageList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private var currentPage = 1

    var image: Photo? = null

    init {
        loadImages()
    }

    fun loadImages() {
        viewModelScope.launch {
            _isLoading.value = true

            val newImages = getPhotosUseCase(page = currentPage)
            _imageList.value += newImages

            _isLoading.value = false
            currentPage++
        }
    }
}
