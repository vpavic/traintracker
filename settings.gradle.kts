buildscript {
    repositories {
        gradlePluginPortal()
    }

    configurations.classpath {
        resolutionStrategy.activateDependencyLocking()
    }

    dependencies {
        val kotlinVersion: String by settings
        classpath("com.diffplug.spotless:spotless-plugin-gradle:+")
        classpath("com.github.ben-manes:gradle-versions-plugin:+")
        classpath("gradle.plugin.com.google.cloud.tools:jib-gradle-plugin:+")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("org.jetbrains.kotlin:kotlin-allopen:$kotlinVersion")
    }
}

rootProject.name = "traintracker"
