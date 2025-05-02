# 📱 EchoChat

**EchoChat** is a real-time chat application for Android developed in **Kotlin** using **CometChat’s Kotlin UI Kit**. It offers an instant messaging experience with a modern UI powered by pre-built components provided by CometChat. This project showcases user login, authentication persistence, and chat interface integration.

---

## ✨ Features

- 🔐 **User Login** using CometChat's authentication system  
- 💬 **One-to-One & Group Messaging** via CometChat UI Kit (pre-built components)
- 🚀 **Fast Onboarding** with automatic session check
- 🔄 **Real-time Presence** and message sync using CometChat services
- 📦 **MVVM-style Structure** for maintainability

---

## 📁 Project Structure

```

com.example.androidchatapp/
├── MyApplication.kt         # Initializes CometChat SDK with settings
├── LoginActivity.kt         # User login and session check
├── MainActivity.kt          # Redirects to chat UI after login
├── res/layout/              # Layout files including login screen
└── AndroidManifest.xml      # Permissions and app configuration

````

---

## 🛠️ Getting Started

### ✅ Prerequisites

- Android Studio (Giraffe or later)
- Kotlin 1.8+
- Gradle 8+
- A CometChat Pro account

### 📦 Dependencies

EchoChat uses the following dependencies:

```kotlin
implementation("com.cometchat:chat-uikit-android:4.3.17")
implementation("com.cometchat:chat-core-android:4.3.17")
````

And includes:

* ViewBinding
* AndroidX Core
* Material 3
* Coroutines

---

## 🔧 Setup Instructions

1. **Clone the Repo**

   ```bash
   git clone https://github.com/your-username/EchoChat.git
   ```

2. **Get CometChat Credentials**

   * Create a free account at [cometchat.com](https://www.cometchat.com)
   * Create a new app and copy the App ID, Auth Key, and Region

3. **Add to `build.gradle.kts`**

   ```kotlin
   buildConfigField("String", "COMET_CHAT_APP_ID", "\"your_app_id\"")
   buildConfigField("String", "COMET_CHAT_AUTH_KEY", "\"your_auth_key\"")
   buildConfigField("String", "COMET_CHAT_REGION", "\"your_region\"")
   ```

4. **Sync & Run**

---

## 🧪 How It Works

* On launch, `MyApplication.kt` initializes the CometChat SDK
* `LoginActivity.kt` checks if the user is already logged in
* If not, the user is prompted to log in using a CometChat UID
* On success, user is redirected to `MainActivity.kt`
* `MainActivity.kt` shows the full CometChat UI

---

