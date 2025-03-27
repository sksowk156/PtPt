plugins {
    alias(libs.plugins.ptpt.android.application)
    alias(libs.plugins.ptpt.android.application.compose)
    alias(libs.plugins.ptpt.android.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.paradise.ptpt"

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    implementation(projects.feature.auth)
    implementation(projects.feature.home)
    implementation(projects.feature.matching)
    implementation(projects.feature.my)
    implementation(projects.feature.record)
    implementation(projects.feature.tracking)

    implementation(projects.core.designsystem)
//    implementation(projects.core.sideeffect)
    implementation(projects.core.data)
    implementation(projects.core.model)
    implementation(projects.core.ui)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.navigation)
    implementation(libs.androidx.compose.material3.adaptive)
    implementation(libs.androidx.compose.material3.adaptive.layout)
    implementation(libs.androidx.compose.material3.adaptive.navigation)
    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(libs.androidx.compose.runtime.tracing)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.kotlinx.coroutines.guava)
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.androidx.lifecycle.runtime.ktx)
}
