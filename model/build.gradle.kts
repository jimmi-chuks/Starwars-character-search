
plugins {
    id("org.jetbrains.kotlin.jvm")
    alias(libs.plugins.kotlin.serialization)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    api(platform(projects.dependenciesPlatform))
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.serialization.json)
}