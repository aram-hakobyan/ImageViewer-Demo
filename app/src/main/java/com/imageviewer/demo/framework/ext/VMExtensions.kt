package com.imageviewer.demo.framework.ext

import android.util.Log
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

fun ViewModel.logError(message: String?) {
    Log.e(this::class.java.name, message ?: "Unknown Error")
}
