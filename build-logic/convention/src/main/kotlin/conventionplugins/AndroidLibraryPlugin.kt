import com.android.build.gradle.LibraryExtension
import extension.androidTargetSdk
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager){
                apply("com.android.library")
                apply("kotlin-android")
            }

            extensions.configure<LibraryExtension> {
                defaultConfig.targetSdk = androidTargetSdk()

                testOptions {
                    targetSdk = project.androidTargetSdk()
                }
                configureKotlinAndroid(this)
            }
        }
    }
}
