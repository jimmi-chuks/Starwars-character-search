
import com.android.build.gradle.LibraryExtension
import extension.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryComposePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("starwars.android.library")
            extensions.getByType<LibraryExtension>().apply {
                configureAndroidCompose(this)
            }
        }
    }

}
