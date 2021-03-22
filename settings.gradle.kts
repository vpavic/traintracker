buildscript {
    repositories {
        gradlePluginPortal()
    }

    configurations.classpath {
        resolutionStrategy.activateDependencyLocking()
    }

    dependencies {
        classpath("com.diffplug.spotless:spotless-plugin-gradle:+")
        classpath("com.github.ben-manes:gradle-versions-plugin:+")
        classpath("gradle.plugin.com.google.cloud.tools:jib-gradle-plugin:+")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.+")
    }
}

rootProject.name = "traintracker"
