plugins {
    alias(libs.plugins.ptpt.android.feature)
    alias(libs.plugins.ptpt.android.library.compose)
}

android {
    namespace = "com.paradise.feature.routine"
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.domain)
    implementation(libs.coil.compose)
    implementation(libs.coil.network)

    testImplementation(libs.hilt.android.testing)
}
