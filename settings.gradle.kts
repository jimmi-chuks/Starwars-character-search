
pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "StarwarsDemo"

include(
    ":model",
    ":shared_test",
    ":domain",
    ":data",
    ":app",
    ":compose-ui",
    ":dependencies-platform"
)
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")