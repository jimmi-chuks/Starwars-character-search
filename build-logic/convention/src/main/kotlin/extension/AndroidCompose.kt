package extension

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies


internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        buildFeatures {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion = project.composeCompilerVersion()
        }

        dependencies {
            val bom = project.libraryDependencyCoordinate("compose-bom")
            add("implementation", platform(bom))
            add("androidTestImplementation", platform(bom))
        }
    }

}
