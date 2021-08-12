pluginManagement {
    plugins {
        val kotlinVersion: String by settings
        id("com.diffplug.spotless").version("5.14.2")
        id("com.github.ben-manes.versions").version("0.39.0")
        id("com.google.cloud.tools.jib").version("3.1.3")
        kotlin("jvm").version(kotlinVersion)
        kotlin("plugin.spring").version(kotlinVersion)
    }
}

rootProject.name = "traintracker"
