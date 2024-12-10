package com.pixabay.demo.core.ext

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun ViewModel.launch(code: suspend () -> Unit) {
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }
    viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
        code.invoke()
    }
}
