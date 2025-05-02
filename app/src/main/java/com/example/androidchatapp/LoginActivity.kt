package com.example.androidchatapp

// LoginActivity.kt

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import com.example.androidchatapp.MyApplication
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.cometchat.chat.core.CometChat
import com.cometchat.chat.exceptions.CometChatException
import com.cometchat.chat.models.User
import com.cometchat.chatuikit.shared.cometchatuikit.CometChatUIKit

//import com.cometchat.uikit.api.CometChatUIKit
import com.example.androidchatapp.databinding.ActivityLoginBinding // Replace with your actual package name
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

class LoginActivity : AppCompatActivity() {

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!MyApplication.isInitialized()) {
            showError("CometChat not initialized")
            return
        }

        setupLoginButton()
        checkLoggedInUser()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun checkLoggedInUser() {
        lifecycleScope.launch {
            try {
                withTimeout(3000L) {
                    CometChatUIKit.getLoggedInUser()?.let {
                        startChatActivity()
                        finish()
                    }
                }
            } catch (e: Exception) {
                Log.e("LoginActivity", "Error checking logged in user", e)
            }
        }
    }

    private fun setupLoginButton() {
        binding.loginButton.setOnClickListener {
            val userId = binding.userIdInput.text.toString().trim()
            if (validateUserId(userId)) {
                performLogin(userId)
            } else {
                showError("Invalid User ID. Must be at least 3 characters and contain no spaces.")
            }
        }
    }

    private fun validateUserId(userId: String): Boolean {
        return userId.length >= 3 && !userId.contains(" ")
    }

    private fun performLogin(userId: String) {
        if (isLoading || !isNetworkAvailable()) {
            showError("Please check your internet connection")
            return
        }

        isLoading = true
        updateUIForLoading(true)

        CometChatUIKit.login(userId, object : CometChat.CallbackListener<User>() {
            override fun onSuccess(user: User) {
                isLoading = false
                updateUIForLoading(false)
                startChatActivity()
                finish()
            }

            override fun onError(e: CometChatException) {
                isLoading = false
                updateUIForLoading(false)
                showError("Login failed: ${e.message}")
            }
        })
    }

    private fun updateUIForLoading(loading: Boolean) {
        binding.apply {
            loginButton.isEnabled = !loading
            progressBar.isVisible = loading
            errorText.isVisible = false
        }
    }

    private fun showError(message: String) {
        binding.errorText.apply {
            text = message
            isVisible = true
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            connectivityManager.activeNetwork != null
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo
            networkInfo != null && networkInfo.isConnected
        }
    }

    private fun startChatActivity() {
        startActivity(Intent(this, MainActivity::class.java))
    }
}
