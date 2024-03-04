plugins {
    alias(libs.plugins.starwars.android.compose.library)
}

android {
    namespace = "com.dani.compose_ui"
}

dependencies {
    implementation(libs.bundles.composeUiBundle)
    implementation(libs.compose.viewmodel)
    implementation(libs.androidx.core)
}