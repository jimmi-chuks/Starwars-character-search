
import com.android.build.api.dsl.ApplicationExtension
import extension.androidCompileSdk
import extension.androidMinSdk
import extension.androidTargetSdk
import extension.configureAndroidCompose
import extension.pluginDependencyCoordinate
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.getByType<ApplicationExtension>().apply {
                defaultConfig {
                    applicationId = "com.dani.starwars"
                    versionCode = 1
                    versionName = "1.0"

                    targetSdk = androidTargetSdk()
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }
                configureKotlinAndroid(this)

                packaging.resources {
                    excludes.addAll(setOf("META-INF/AL2.0", "META-INF/LGPL2.1"))
                    excludes += "/META-INF/{AL2.0,LGPL2.1}"
                }
            }
        }
    }

}
