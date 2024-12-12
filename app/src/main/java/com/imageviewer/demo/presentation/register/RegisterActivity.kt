package com.imageviewer.demo.presentation.register

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.imageviewer.demo.R
import com.imageviewer.demo.databinding.ActivityRegisterBinding
import com.imageviewer.demo.framework.ext.showErrorMessage
import com.imageviewer.demo.presentation.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
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
                viewModel.uiState.collect { state ->
                    when (state) {
                        RegisterViewModel.RegisterUIState.Error -> showErrorMessage()
                        RegisterViewModel.RegisterUIState.Idle -> Unit
                        RegisterViewModel.RegisterUIState.Success -> {
                            onLoginSuccess()
                        }

                        RegisterViewModel.RegisterUIState.Cancelled -> {
                            onBackPressedDispatcher.onBackPressed()
                        }
                    }
                }
            }
        }
    }

    private fun onLoginSuccess() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}