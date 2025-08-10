@file:Suppress("DEPRECATION")

import org.gradle.kotlin.dsl.androidTestImplementation
import org.gradle.kotlin.dsl.debugImplementation
import org.gradle.kotlin.dsl.kaptTest

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("org.jetbrains.kotlin.kapt")
    id("com.google.dagger.hilt.android")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.example.note"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.note"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.example.note.HiltTestRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    hilt {
        enableTransformForLocalTests = true
    }
    kapt {
        correctErrorTypes = true
    }
    packaging {
        resources {
            excludes += "/META-INF/LICENSE.md"
            excludes += "/META-INF/LICENSE-notice.md"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    testImplementation(libs.junit.junit)
    testImplementation(libs.dagger.hilt.android.testing)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    androidTestImplementation (libs.androidx.junit.v130)
    androidTestImplementation (libs.androidx.espresso.core.v370)
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // Compose dependencies
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.hilt.navigation.compose)

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    //Dagger - Hilt
//    implementation(libs.hilt.android)

    // Hilt Compose integration
    implementation(libs.androidx.hilt.navigation.compose)
    androidTestImplementation ("androidx.startup:startup-runtime:1.2.0")


    implementation(libs.hilt.android.v2562)
    implementation(libs.androidx.startup.runtime)



    // Room
    implementation(libs.androidx.room.runtime)
    //noinspection KaptUsageInsteadOfKsp
    kapt(libs.androidx.room.compiler)
    kapt(libs.dagger.hilt.compiler)

    // Kotlin Extensions and Coroutines support for Room
    implementation(libs.androidx.room.ktx)

    // Local unit tests
    testImplementation (libs.androidx.core)
    testImplementation (libs.junit)
    testImplementation (libs.androidx.core.testing)
    testImplementation (libs.kotlinx.coroutines.test)
    testImplementation (libs.truth)
    testImplementation (libs.mockwebserver)
    testImplementation (libs.mockk)
    debugImplementation (libs.ui.test.manifest)
    kaptTest (libs.hilt.android.compiler)

    // Instrumentation tests
    androidTestImplementation (libs.dagger.hilt.android.testing)
    kaptAndroidTest (libs.hilt.android.compiler)
    implementation (libs.kotlin.stdlib)
    androidTestImplementation (libs.junit)
    androidTestImplementation (libs.kotlinx.coroutines.test.v151)
    androidTestImplementation (libs.androidx.core.testing.v210)
    androidTestImplementation (libs.truth)
    androidTestImplementation (libs.androidx.junit.v130)
    androidTestImplementation (libs.core.ktx)
    androidTestImplementation (libs.mockwebserver)
    androidTestImplementation (libs.mockk.android)
    androidTestImplementation (libs.androidx.runner)
    implementation("androidx.compose.ui:ui:1.8.3")


}