
plugins {
    `java-library`
    kotlin("jvm")
}

dependencies {
    api(platform(projects.dependenciesPlatform))
    implementation(projects.model)
    api(libs.kotlin.stdlib)
    implementation(kotlin("stdlib-jdk8"))
}
