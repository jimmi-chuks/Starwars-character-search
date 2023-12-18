
plugins {
    alias(libs.plugins.starwars.android.compose.application)
    alias(libs.plugins.kotlin.serialization)
    kotlin("kapt")
}

android {
    namespace = "com.dani.starwars"
    defaultConfig {

        multiDexEnabled = true
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    configurations.all {
        resolutionStrategy.force("com.google.code.findbugs:jsr305:1.3.9")
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {
    api(platform(projects.dependenciesPlatform))
    implementation(projects.domain)
    implementation(projects.data)
    implementation(projects.model)
    implementation(projects.sharedTest)
    implementation(projects.composeUi)

    implementation(libs.bundles.uiBundle)
    implementation(libs.kotlin.stdlib)
    implementation(libs.coroutines.core)
    implementation(libs.bundles.composeUiBundle)
    implementation(libs.bundles.lifecycleBundle)

    implementation(libs.bundles.arrowBundle)
    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.bundles.tests)
    androidTestImplementation(libs.bundles.tests)
    androidTestImplementation(libs.bundles.unitTest)
    androidTestImplementation(libs.bundles.androidTest)

    testImplementation(libs.bundles.unitTest)

    implementation(libs.dagger.core)
    androidTestImplementation(libs.compose.uiTest)
//    debugImplementation(libs.compose.uiTest)
    kapt(libs.dagger.compiler)
    compileOnly(libs.javaxAnnotation)

    // retrofit
    implementation(libs.bundles.retrofit)
}