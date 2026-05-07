plugins {
    id 'com.android.application'
    id 'org.jetbrains.kotlin.android'
}

android {
    namespace 'com.university.newsapp'
    compileSdk 35

    defaultConfig {
        applicationId "com.university.newsapp"
        minSdk 24
        targetSdk 35
        versionCode 1
        versionName "1.0"
    }

    buildFeatures {
        viewBinding true
    }
}

dependencies {
    implementation 'androidx.core:core-ktx:1.13.1'
    implementation 'androidx.appcompat:appcompat:1.7.0'
    implementation 'com.google.android.material:material:1.12.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'

    // Retrofit
    implementation 'com.squareup.retrofit2:retrofit:2.11.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.11.0'

    // OkHttp Logging
    implementation 'com.squareup.okhttp3:logging-interceptor:4.12.0'

    // Coroutines
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1'

    // Glide
    implementation 'com.github.bumptech.glide:glide:4.16.0'

    // RecyclerView & CardView
    implementation 'androidx.recyclerview:recyclerview:1.3.2'
    implementation 'androidx.cardview:cardview:1.0.0'
}