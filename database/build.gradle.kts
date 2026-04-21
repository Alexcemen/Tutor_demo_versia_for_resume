plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.room)
    alias(libs.plugins.ksp)
}

room {
    schemaDirectory("$projectDir/schemas")
}

android {
    namespace = "ru.project.tutor.database"
    compileSdk = 36

    defaultConfig {
        minSdk = 29

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    ksp(libs.room.compiler) {
        exclude(group = "org.jetbrains", module = "annotations")
        exclude(group = "com.intellij", module = "annotations")
    }
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.room.testing)
}
