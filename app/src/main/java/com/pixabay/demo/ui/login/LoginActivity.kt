package com.pixabay.demo.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.pixabay.demo.R
import com.pixabay.demo.databinding.ActivityLoginBinding
import com.pixabay.demo.ui.home.MainActivity
import com.pixabay.demo.ui.register.RegisterActivity
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
                viewModel.uiState.collectLatest { state ->
                    when (state) {
                        LoginViewModel.LoginUIState.Error -> Unit
                        LoginViewModel.LoginUIState.Idle -> Unit
                        LoginViewModel.LoginUIState.Success -> {
                            onLoginSuccess()
                        }
                    }
                }

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
                MainActivity::class.java
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