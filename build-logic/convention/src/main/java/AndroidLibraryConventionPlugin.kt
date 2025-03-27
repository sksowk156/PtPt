import com.android.build.api.dsl.LibraryExtension
import com.paradise.convention.ExtensionType
import com.paradise.convention.configureBuildTypes
import com.paradise.convention.configureKotlinAndroid
import com.paradise.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)

                configureBuildTypes(
                    commonExtension = this,
                    extensionType = ExtensionType.LIBRARY,
                )

                defaultConfig {
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    consumerProguardFiles("consumer-rules.pro")
                }
            }

            dependencies {
                add("implementation", libs.findLibrary("androidx.paging.common").get())
                add("testImplementation", libs.findLibrary("kotlin.test").get())
            }
        }
    }
}
