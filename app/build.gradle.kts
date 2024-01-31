plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.android.kotlin)
    kotlin("kapt")
    alias(libs.plugins.hilt.android)
}

android {
    namespace = "com.fzellner.marvelappcompose"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.fzellner.marvelappcompose"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }

}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(libs.bundles.core)

    implementation(platform(libs.okhttp.bom))
    implementation(libs.bundles.network)
    implementation(libs.bundles.moshi)

    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)

    implementation(libs.bundles.hilt)
    testImplementation(project(":common"))
    kapt(libs.hiltkapt)

    testRuntimeOnly(libs.jupiterEngine)
    testImplementation(libs.bundles.testing)

    implementation(project(":designtoken"))
    implementation(project(":comicList"))
    implementation(project(":comicDetails"))
}