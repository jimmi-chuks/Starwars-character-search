
plugins {
    alias(libs.plugins.starwars.android.library)
    alias(libs.plugins.kotlin.serialization)
    id( "kotlin-kapt")
}

android {
    namespace = "com.dani.domain"
}

dependencies {
    api(platform(projects.dependenciesPlatform))
    implementation(projects.data)
    implementation(projects.model)

    implementation(libs.androidx.core)
    implementation(libs.bundles.coroutineAndroid)

    implementation(libs.bundles.arrowBundle)
    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.bundles.unitTest)
    androidTestImplementation(libs.bundles.androidTest)


    testImplementation(libs.bundles.tests)
    androidTestImplementation(libs.bundles.tests)

    implementation(libs.kotlin.stdlib)

    api(libs.dagger.core)
    kapt(libs.dagger.compiler)
    compileOnly(libs.javaxAnnotation)

    // retrofit
    implementation(libs.bundles.retrofit)
}