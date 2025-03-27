plugins {
    alias(libs.plugins.ptpt.android.library)
    alias(libs.plugins.ptpt.android.hilt)
    id("kotlinx-serialization")
}

android {
    namespace = "com.paradise.core.data"
}

dependencies {
//    api(projects.core.database)
//    api(projects.core.network)

    testImplementation(libs.androidx.paging.common)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.kotlinx.serialization.json)

    androidTestImplementation(libs.androidx.test.core)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.kotlinx.coroutines.test)
}
