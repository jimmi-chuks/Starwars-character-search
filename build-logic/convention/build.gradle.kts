
plugins {
    `kotlin-dsl`
}

group = "com.dani.starwars.buildlogic"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of("17"))
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}


gradlePlugin {
    plugins {
        register("starwarsAndroidApplication") {
            id = "starwars.android.application"
            implementationClass = "AndroidApplicationPlugin"
        }
        register("starwarsAndroidApplicationCompose") {
            id = "starwars.android.applicationCompose"
            implementationClass = "AndroidComposeApplicationPlugin"
        }
        register("androidLibrary") {
            id = "starwars.android.library"
            implementationClass = "AndroidLibraryPlugin"
        }
        register("androidLibraryCompose") {
            id = "starwars.android.libraryCompose"
            implementationClass = "AndroidLibraryComposePlugin"
        }

        register("jvmLibrary") {
            id = "starwars.android.kotlinJvm"
            implementationClass = "JvmLibraryPlugin"
        }
    }
}