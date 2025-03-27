plugins {
    alias(libs.plugins.ptpt.android.library)
    alias(libs.plugins.ptpt.android.library.compose)
}

android {
    namespace = "com.paradise.core.ui"
}

dependencies {
    api(projects.core.designsystem)
    api(projects.core.model)

    implementation(libs.coil.compose)
    implementation(libs.coil.network)
}
