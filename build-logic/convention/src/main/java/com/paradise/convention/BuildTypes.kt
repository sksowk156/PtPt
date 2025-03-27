package com.paradise.convention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.BuildType
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import com.paradise.convention.ExtensionType
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

internal fun Project.configureBuildTypes(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    extensionType: ExtensionType,
    ) {
    commonExtension.run {
        buildFeatures {
            buildConfig = true
        }
        val projectVersionName = libs.findVersion("projectVersionName").get().toString()
        val projectVersionCode = libs.findVersion("projectVersionCode").get().toString()

        when (extensionType) {
            ExtensionType.APPLICATION -> {
                extensions.configure<ApplicationExtension> {
                    buildTypes {
                        debug {
                            configureDebugBuildType(
                                projectVersionName,
                                projectVersionCode,
                            )
                            isDebuggable = true
                        }
                        release {
                            configureReleaseBuildType(
                                commonExtension,
                                projectVersionName,
                                projectVersionCode,
                            )
                            isDebuggable = false
                        }
                    }
                }
            }

            ExtensionType.LIBRARY -> {
                extensions.configure<LibraryExtension> {
                    buildTypes {
                        debug {
                            configureDebugBuildType(
                                projectVersionName,
                                projectVersionCode,
                            )
                        }
                        release {
                            configureReleaseBuildType(
                                commonExtension,
                                projectVersionName,
                                projectVersionCode,
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun BuildType.configureDebugBuildType(
    projectVersionName: String,
    projectVersionCode: String,
) {
    buildConfigField("String", "VERSION_NAME", "\"$projectVersionName\"")
    buildConfigField("String", "VERSION_CODE", "\"$projectVersionCode\"")
}

private fun BuildType.configureReleaseBuildType(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    projectVersionName: String,
    projectVersionCode: String,
) {
    buildConfigField("String", "VERSION_NAME", "\"$projectVersionName\"")
    buildConfigField("String", "VERSION_CODE", "\"$projectVersionCode\"")

    proguardFiles(
        commonExtension.getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro",
    )
}
