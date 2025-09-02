plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.dagger.hilt)
}

android {
    namespace = "com.abhang.matchmate"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.abhang.matchmate"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    testImplementation(libs.google.truth)
    androidTestImplementation(libs.google.truth)
    implementation(libs.google.gson)
    implementation(libs.retrofit.converter)
    implementation(libs.okhttp3.interceptor)
    implementation(libs.kotlin.coroutine.android)
    implementation(libs.kotlin.coroutine.core)
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
    implementation(libs.room.ktx)
    testImplementation(libs.room.testing)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.activity.ktx)
    implementation(libs.fragment.ktx)
    implementation(libs.lifecycle.extension)
    implementation(libs.lifecycler.vm)
    implementation(libs.lifecycle.livedata)
    implementation(libs.lifecycle.runtime)
    kspAndroidTest(libs.hilt.compiler)
    androidTestImplementation(libs.junit.ext)
    androidTestImplementation(libs.nav.testing)
    testImplementation(libs.core.testing)
    androidTestImplementation(libs.core.testing)
    implementation(libs.glide)
    androidTestImplementation(libs.test.core)
    androidTestImplementation(libs.test.rules)
    androidTestImplementation(libs.test.runner)

}