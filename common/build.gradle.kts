import java.io.FileInputStream
import java.util.Properties
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.android.kotlin)
    alias(libs.plugins.hilt.android)
    kotlin("kapt")
}

android {
    namespace = "com.fzellner.marvelappcompose.network"
    compileSdk = 34

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    val properties = Properties().apply {
        load(FileInputStream(File(rootDir.path,"keys.properties")))
    }
    val publicApi = properties["apiPublicKey"]
    val privateApi = properties["apiPrivateKey"]


    buildTypes {
        defaultConfig {
            buildConfigField(
                type = "String",
                name = "apiPrivateKey",
                value = privateApi as String
            )
            buildConfigField(
                type = "String",
                name = "apiPublicKey",
                value = publicApi as String
            )
            buildConfigField(
                type = "String",
                name = "baseUrl",
                value = project.findProperty("marvelApiUrl") as String
            )
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

}
kapt {
    correctErrorTypes = true
}



dependencies {
    implementation(platform(libs.okhttp.bom))
    implementation(libs.bundles.network)
    implementation(libs.bundles.moshi)
    implementation(libs.bundles.hilt)
    kapt(libs.hiltkapt)

}