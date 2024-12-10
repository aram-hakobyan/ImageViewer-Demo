package com.pixabay.demo.ui.register

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.pixabay.demo.R
import com.pixabay.demo.databinding.ActivityRegisterBinding
import com.pixabay.demo.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest { state ->
                    when (state) {
                        RegisterViewModel.RegisterUIState.Error -> Unit
                        RegisterViewModel.RegisterUIState.Idle -> Unit
                        RegisterViewModel.RegisterUIState.Success -> {
                            onLoginSuccess()
                        }
                    }
                }

                viewModel.isBackClicked.collectLatest { isBackClicked ->
                    if (isBackClicked) {
                        onBackPressedDispatcher.onBackPressed()
                    }
                }
            }
        }
    }

    private fun onLoginSuccess() {
        startActivity(
            Intent(
                this@RegisterActivity,
                HomeActivity::class.java
            )
        )
        finish()
    }
}