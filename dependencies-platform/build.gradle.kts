plugins {
    id("java-platform")
}

dependencies {
        constraints {
                api(libs.livedata)
                api(libs.viewmodel)
                api(libs.bundles.composeUiBundle)
                api(libs.bundles.arrowBundle)
                api(libs.coroutines.android)
                api(libs.coroutines.core)
                api(libs.coroutines.test)
                api(libs.room.compiler)
                api(libs.room.ktx)
                api(libs.room.runtime)
                api(libs.room.test)
                api(libs.dagger.core)
                api(libs.dagger.compiler)
                api(libs.javaxAnnotation)
                api(libs.kotlin.gradlePlugin)
                api(libs.kotlin.stdlib)
                api(libs.retrofit.core)
                api(libs.retrofit.kotlin.serialization)
                api(libs.loggingInterceptor)
    }
}