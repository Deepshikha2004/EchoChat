package com.example.androidchatapp

import android.Manifest
import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import com.cometchat.chat.core.CometChat
import com.cometchat.chat.exceptions.CometChatException
import com.cometchat.chatuikit.shared.cometchatuikit.CometChatUIKit
import com.cometchat.chatuikit.shared.cometchatuikit.UIKitSettings

object CometChatConstants {
    val APP_ID = BuildConfig.COMET_CHAT_APP_ID
    val AUTH_KEY = BuildConfig.COMET_CHAT_AUTH_KEY
    val REGION = BuildConfig.COMET_CHAT_REGION
}

class MyApplication : Application() {

    companion object {
        private var isInitialized = false
        private const val PERMISSION_REQUEST_CODE = 1001

        fun isInitialized() = isInitialized

        fun logout(context: Context, callback: () -> Unit) {
            CometChatUIKit.logout(object : CometChat.CallbackListener<String>() {
                override fun onSuccess(message: String) {
                    Log.d("CometChat", "Logout successful: $message")
                    callback()
                }

                override fun onError(e: CometChatException) {
                    Log.e("CometChat", "Logout failed: ${e.message}")
                }
            })
        }
    }

    override fun onCreate() {
        super.onCreate()
        initCometChat { success ->
            isInitialized = success
        }
    }

    private fun initCometChat(onComplete: (Boolean) -> Unit) {
        if (CometChatConstants.APP_ID.isEmpty() ||
            CometChatConstants.AUTH_KEY.isEmpty() ||
            CometChatConstants.REGION.isEmpty()
        ) {
            Log.e("CometChat", "Missing credentials - check your build.gradle")
            onComplete(false)
            return
        }

        val uiKitSettings = UIKitSettings.UIKitSettingsBuilder()
            .setRegion(CometChatConstants.REGION)
            .setAppId(CometChatConstants.APP_ID)
            .setAuthKey(CometChatConstants.AUTH_KEY)
            .subscribePresenceForAllUsers()
            .build()

        CometChatUIKit.init(applicationContext, uiKitSettings, object : CometChat.CallbackListener<String?>() {
            override fun onSuccess(successString: String?) {
                Log.d("CometChat", "Initialization completed successfully")
                onComplete(true)
            }

            override fun onError(e: CometChatException?) {
                Log.e("CometChat", "Initialization failed: ${e?.message}")
                onComplete(false)
            }
        })
    }

    fun checkAndRequestPermissions(activity: Activity) {
        val permissions = mutableListOf(
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissions.addAll(
                listOf(
                    Manifest.permission.READ_MEDIA_IMAGES,
                    Manifest.permission.READ_MEDIA_VIDEO
                )
            )
        }

        ActivityCompat.requestPermissions(
            activity,
            permissions.toTypedArray(),
            PERMISSION_REQUEST_CODE
        )
    }
}
