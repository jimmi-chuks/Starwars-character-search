// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlin.android)  apply false
    alias(libs.plugins.kotlin.serialization) apply false
    kotlin("kapt") version "1.9.20" apply false
}

//allprojects {
//    configurations.all {
//        resolutionStrategy.force("org.jetbrains:annotations:23.0.0")
//    }
//
//}