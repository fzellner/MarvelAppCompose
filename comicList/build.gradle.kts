@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.android.kotlin)
    alias(libs.plugins.hilt.android)
    kotlin("kapt")

}

android {
    namespace = "com.fzellner.marvelappcompose.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

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
    hilt {
        enableExperimentalClasspathAggregation = true
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(project(":common"))
    implementation(project(":designtoken"))
    implementation(libs.androidx.ktx)
    implementation(libs.bundles.network)
    implementation(libs.bundles.core)
    implementation(libs.bundles.hilt)
    implementation(libs.bundles.moshi)
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)
    testImplementation(libs.bundles.testing)
    testImplementation(project(":common"))
    kapt(libs.hiltkapt)
}