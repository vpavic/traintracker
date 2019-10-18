pluginManagement {
    plugins {
        kotlin("jvm") version PluginVersions.kotlin
        id("com.diffplug.gradle.spotless") version PluginVersions.spotless
        id("com.github.ben-manes.versions") version PluginVersions.versions
        id("com.google.cloud.tools.jib") version PluginVersions.jib
        id("org.jetbrains.kotlin.plugin.spring") version PluginVersions.kotlin
        id("org.jlleitschuh.gradle.ktlint") version PluginVersions.ktlint
    }

    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "org.springframework.boot" ->
                    useModule("org.springframework.boot:spring-boot-gradle-plugin:${PluginVersions.springBoot}")
            }
        }
    }

    repositories {
        gradlePluginPortal()
        maven(url = "https://repo.spring.io/libs-milestone")
    }
}

rootProject.name = "traintracker"
