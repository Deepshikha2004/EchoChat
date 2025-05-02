plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.example.androidchatapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.androidchatapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        manifestPlaceholders["file_provider"] = "com.example.androidchatapp"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // BuildConfig fields for CometChat credentials
        buildConfigField("String", "COMET_CHAT_APP_ID", "\"273697874146bc9e\"")
        buildConfigField("String", "COMET_CHAT_AUTH_KEY", "\"14e7142bcad5d096a02d29e22c598201b3494082\"")
        buildConfigField("String", "COMET_CHAT_REGION", "\"IN\"")

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        compose = true
        viewBinding = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs += listOf("-Xopt-in=kotlin.RequiresOptIn")
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Compose & AndroidX (Version catalog)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // CometChat UIKit
    implementation("com.cometchat:chat-uikit-android:4.3.17")
    implementation("com.cometchat:chat-core-android:4.3.17")
    implementation("androidx.appcompat:appcompat:1.6.1")


    // Other Kotlin/AndroidX libraries
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")

    // Use JitPack-compatible version (4.3.4 or 4.3.2-beta.1 etc.)
    //implementation("com.github.cometchat-pro:androiduikit:4.3.4")


    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
