package com.example.androidchatapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cometchat.chatuikit.shared.cometchatuikit.CometChatUIKit
import com.example.androidchatapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkAuthAndSetup()
    }

    private fun checkAuthAndSetup() {
        if (CometChatUIKit.getLoggedInUser() == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        } else {
            setupChatInterface()
        }
    }

    private fun setupChatInterface() {
        // No additional setup is needed unless you want to customize more
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
