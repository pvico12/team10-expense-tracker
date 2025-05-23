import java.util.Properties
import java.io.FileInputStream

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.gms.google-services") version "4.3.8"
}


val keystoreProperties = Properties()
val keystorePropertiesFile = rootProject.file("key.properties")
if (keystorePropertiesFile.exists()) {
    FileInputStream(keystorePropertiesFile).use { fileInputStream ->
        keystoreProperties.load(fileInputStream)
    }
} else {
    throw GradleException("Missing key.properties file")
}


android {
    namespace = "com.cs446.expensetracker"
    compileSdk = 35

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        applicationId = "com.cs446.expensetracker"
        minSdk = 24
        targetSdk = 35
        versionCode = 3
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            keyAlias = keystoreProperties["keyAlias"] as String
            keyPassword = keystoreProperties["keyPassword"] as String
            storeFile = keystoreProperties["storeFile"]?.let { file(it as String) }
            storePassword = keystoreProperties["storePassword"] as String
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
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

    testOptions {
        unitTests {
            isReturnDefaultValues = true
        }
    }
}

dependencies {
    // Core & Compose dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.runtime.livedata)
    implementation("androidx.compose.material:material-icons-extended:1.5.1")
    implementation(libs.androidx.navigation.testing.android)
    implementation(libs.androidx.espresso.contrib)

    // Testing dependencies for unit tests.
    testImplementation(libs.junit)

    // Android instrumented tests:
    androidTestImplementation("androidx.test:core:1.5.0")
    androidTestImplementation("androidx.test:runner:1.5.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Compose UI testing dependencies (using the Compose BOM)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation("com.squareup.okhttp3:mockwebserver:4.9.3")
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    androidTestImplementation("org.mockito:mockito-core:4.5.1")
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.7.8")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.7.8")

    // Additional dependencies
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
    val nav_version = "2.8.5"
    implementation("androidx.navigation:navigation-compose:$nav_version")

    val retrofitVersion = "2.11.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")

    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    implementation("com.auth0:java-jwt:4.0.0")
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
    implementation("com.google.android.gms:play-services-maps:19.0.0")
    implementation("com.google.maps.android:maps-compose:2.13.0")
    implementation("com.google.android.libraries.places:places:4.1.0")
    implementation("com.google.maps.android:places-ktx:3.1.1")
    implementation("com.google.android.material:material:1.11.0")

    implementation(platform("com.google.firebase:firebase-bom:33.10.0"))
    implementation("com.google.firebase:firebase-messaging")
    implementation("io.coil-kt:coil-compose:2.5.0")
    implementation("io.coil-kt:coil-gif:2.5.0")
}

apply(plugin = "com.google.gms.google-services")
