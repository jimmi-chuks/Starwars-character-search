package extension

import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.plugin.use.PluginDependency

internal val Project.libsVersionCatalog: VersionCatalog
    get() = extensions.getByType(VersionCatalogsExtension::class.java).named("libs")

internal fun Project.catalogStringVersion(versionName: String): String =
    libsVersionCatalog.findVersion(versionName).get().requiredVersion

internal fun Project.catalogIntVersion(versionName: String): Int =
    libsVersionCatalog.findVersion(versionName).get().requiredVersion.toInt()

internal fun Project.libraryDependencyCoordinate(alias: String): MinimalExternalModuleDependency =
    libsVersionCatalog.findLibrary(alias).orElseThrow().get()

internal fun Project.pluginDependencyCoordinate(alias: String) : PluginDependency =
    libsVersionCatalog.findPlugin(alias).orElseThrow().get()

internal fun Project.androidCompileSdk(): Int = catalogIntVersion("androidCompileSdk")

internal fun Project.androidTargetSdk(): Int = catalogIntVersion("androidTargetSdk")

internal fun Project.androidMinSdk(): Int = catalogIntVersion("androidMinSdk")

internal fun Project.composeCompilerVersion(): String = catalogStringVersion("composeCompilerVersion")

internal fun jvmTargetResolution(): JavaVersion = JavaVersion.VERSION_17

internal fun Project.projectJvmTarget(): String = catalogStringVersion("jvmTarget")
