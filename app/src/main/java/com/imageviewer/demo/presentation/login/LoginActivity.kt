package com.imageviewer.demo.presentation.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.imageviewer.demo.R
import com.imageviewer.demo.databinding.ActivityLoginBinding
import com.imageviewer.demo.framework.ext.showErrorMessage
import com.imageviewer.demo.presentation.home.HomeActivity
import com.imageviewer.demo.presentation.register.RegisterActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        LoginViewModel.LoginUIState.Error -> showErrorMessage()
                        LoginViewModel.LoginUIState.Idle -> Unit
                        LoginViewModel.LoginUIState.Success -> {
                            onLoginSuccess()
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isRegisterClicked.collectLatest { isRegisterClicked ->
                    if (isRegisterClicked) {
                        onRegisterClicked()
                    }
                }
            }
        }
    }

    private fun onLoginSuccess() {
        startActivity(
            Intent(
                this@LoginActivity,
                HomeActivity::class.java
            )
        )
        finish()
    }

    private fun onRegisterClicked() {
        startActivity(
            Intent(
                this@LoginActivity,
                RegisterActivity::class.java
            )
        )
    }
}