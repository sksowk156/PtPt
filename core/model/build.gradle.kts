plugins {
    alias(libs.plugins.ptpt.jvm.library)
}

dependencies {
    compileOnly(libs.compose.stable.marker)
    api(libs.kotlinx.datetime)
}
