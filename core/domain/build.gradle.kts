plugins {
    alias(libs.plugins.ptpt.android.library)
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.paradise.core.domain"
}

dependencies {
    api(projects.core.data)
    api(projects.core.model)

    implementation(libs.javax.inject)

    testImplementation(libs.kotlinx.coroutines.test)
}
