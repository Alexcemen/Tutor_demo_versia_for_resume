plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "ru.project.tutor"
    compileSdk = 36

    defaultConfig {
        applicationId = "ru.project.tutor"
        minSdk = 29
        targetSdk = 36
        versionCode = 6
        versionName = "1.5"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        buildFeatures {
            buildConfig = true
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "adUnitId", "\"demo-interstitial-yandex\"")
            buildConfigField("String", "bannerUnitId", "\"demo-banner-yandex\"")
            buildConfigField("String", "bannerTestResultUnitId", "\"demo-banner-yandex\"")
        }
        debug {
            buildConfigField("String", "adUnitId", "\"demo-interstitial-yandex\"")
            buildConfigField("String", "bannerUnitId", "\"demo-banner-yandex\"")
            buildConfigField("String", "bannerTestResultUnitId", "\"demo-banner-yandex\"")
        }
        flavorDimensions("client")
        productFlavors {
            create("google") {
                dimension = "client"
                buildConfigField("String", "flavourName", "\"google\"")
            }
            create("rustore") {
                dimension = "client"
                buildConfigField("String", "flavourName", "\"rustore\"")
            }
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
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.media3.common.ktx)
    implementation(libs.androidx.compose.material)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(platform(libs.koin.bom))
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)
    implementation(libs.koin.androidx.workmanager)
    implementation(libs.kotlin.serialization.core)
    implementation(libs.kotlin.serialization.json)
    implementation(libs.gson)
    implementation(libs.navigation.ui)
    implementation(libs.navigation.lifecycle)
    implementation(libs.navigation.runtime)
    implementation(libs.room.runtime)
    implementation(libs.yandex.ads)

    implementation(libs.androidx.work.runtime.ktx)

    implementation(project(":common_ui"))
    implementation(project(":database"))
}
