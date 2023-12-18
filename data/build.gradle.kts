plugins {
    alias(libs.plugins.starwars.android.library)
    alias(libs.plugins.kotlin.serialization)
    kotlin("kapt")
}

android {
    namespace = "com.dani.data"
}

dependencies {

    api(platform(projects.dependenciesPlatform))
    implementation(projects.model)
    testImplementation(projects.sharedTest)

    implementation(libs.androidx.core)
    implementation(libs.kotlin.stdlib)
    api(libs.coroutines.core)
    api(libs.coroutines.android)

    api(libs.room.runtime)
    kapt(libs.room.compiler)
    implementation(libs.room.ktx)

    implementation(libs.bundles.arrowBundle)
    implementation(libs.kotlinx.serialization.json)

    // retrofit
    implementation(libs.bundles.retrofit)

    // dagger
    implementation(libs.dagger.core)
    kapt(libs.dagger.compiler)
    compileOnly(libs.javaxAnnotation)

    // test
    androidTestImplementation(libs.bundles.unitTest)
    androidTestImplementation(libs.bundles.androidTest)

    testImplementation(libs.bundles.tests)
    androidTestImplementation(libs.bundles.tests)

    testImplementation(libs.bundles.unitTest)
    testImplementation(libs.bundles.androidTest)
}