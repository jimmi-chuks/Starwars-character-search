import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    kotlin("kapt") version "1.9.20" apply false
}

group = "com.dani.starwars.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KaptGenerateStubs> {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.android.gradle)
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