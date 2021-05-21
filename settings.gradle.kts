pluginManagement {
    plugins {
        val kotlinVersion: String by settings
        id("com.diffplug.spotless").version("5.12.5")
        id("com.github.ben-manes.versions").version("0.38.0")
        id("com.google.cloud.tools.jib").version("3.0.0")
        kotlin("jvm").version(kotlinVersion)
        kotlin("plugin.spring").version(kotlinVersion)
    }
}

rootProject.name = "traintracker"
