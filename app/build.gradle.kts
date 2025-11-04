import java.util.Properties
import kotlin.apply

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.hilt)
    alias(libs.plugins.kotlin.kapt)
    id("org.jetbrains.kotlin.plugin.serialization") version "2.2.20"
}

android {

    // load file config.properties
    val props = Properties().apply {
        val file = rootProject.file("config.properties")
        if (file.exists()) file.inputStream().use { load(it) }
        else error("Missing config.properties")
    }

    // load file key.properties
    val key = Properties().apply {
        val file = rootProject.file("key.properties")
        if (file.exists()) file.inputStream().use { load(it) }
        else error("Missing config.properties")
    }

    namespace = props["APPLICATION"].toString()
    compileSdk = props["COMPILE_SDK"].toString().toInt()

    defaultConfig {
        applicationId = props["APPLICATION"].toString()
        minSdk = props["MIN_SDK"].toString().toInt()
        targetSdk = props["TARGET_SDK"].toString().toInt()
        versionCode = props["VERSION_CODE"].toString().toInt()
        versionName = props["VERSION_NAME"].toString()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    flavorDimensions += "environment"

    productFlavors {
        create("dev") {
            dimension = "environment"
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"
            resValue("string", "app_name", "${props["APP_NAME"]} - dev")
            buildConfigField("String", "BASE_URL", "\"${props["BASE_URL_DEV"]}\"")
        }

        create("stg") {
            dimension = "environment"
            applicationIdSuffix = ".stg"
            versionNameSuffix = "-stg"
            resValue("string", "app_name", "${props["APP_NAME"]} - stg")
            buildConfigField("String", "BASE_URL", "\"${props["BASE_URL_STG"]}\"")
        }

        create("prod") {
            dimension = "environment"
            resValue("string", "app_name", props["APP_NAME"].toString())
            buildConfigField("String", "BASE_URL", "\"${props["BASE_URL_PROD"]}\"")
        }
    }

    signingConfigs {
        create("release") {
            storeFile = key["storeFile"]?.let { file(it) }
            storePassword = key["storePassword"].toString()
            keyAlias = key["keyAlias"].toString()
            keyPassword = key["keyPassword"].toString()
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("debug")
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("release")
            proguardFiles( getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    // ================== Navigation ==================
    implementation(libs.androidx.navigation.compose)

    // ================== Hilt ==================
    implementation(libs.hilt.android)
    implementation(libs.androidx.preference.ktx)
    implementation(libs.androidx.material3)
    kapt(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // ================== Lifecycle / ViewModel ==================
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    // ================== Coroutines ==================
    implementation(libs.kotlinx.coroutines.android)

    // ================== Network ==================
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit)

    // ================== Serialization ==================
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit.kotlinx.serialization)

    // ================== Icons (Compose) ==================
    implementation(libs.androidx.compose.material.icons.extended)

    // ================== Image loading ==================
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.hilt.work)
    androidTestImplementation(libs.androidx.work.testing)

    // ================== WorkManager && work-hilt ==================
    implementation(libs.androidx.work.runtime.ktx)

    // ================== datastore ==================
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.datastore.core)

    // ================== Arrow-core ==================
    implementation(libs.arrow.core)

    // ================== AppCompat (chỉ giữ nếu còn View/XML) ==================
    implementation(libs.androidx.appcompat)

    // ================== coil ==================
    implementation(libs.coil3.compose)



    // ================== Unit Testing ==================
    testImplementation(libs.mockk)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.turbine)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.truth)
    testImplementation(libs.robolectric)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // ================== Android Testing ==================
    androidTestImplementation(libs.mockk)
    androidTestImplementation(libs.mockito.core)
    androidTestImplementation(libs.mockito.kotlin)
}