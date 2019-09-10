import org.springframework.boot.gradle.plugin.SpringBootPlugin

plugins {
    java
    kotlin("jvm") version PluginVersions.kotlin
    id("com.diffplug.gradle.spotless") version PluginVersions.spotless
    id("com.github.ben-manes.versions") version PluginVersions.versions
    id("com.google.cloud.tools.jib") version PluginVersions.jib
    id("org.jetbrains.kotlin.plugin.spring") version PluginVersions.kotlin
    id("org.jlleitschuh.gradle.ktlint") version PluginVersions.ktlint
    id("org.springframework.boot") version PluginVersions.springBoot
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
}

repositories {
    mavenCentral()
    maven(url = "https://repo.spring.io/libs-milestone")
}

val developmentOnly: Configuration by configurations.creating

configurations {
    if (isProfileActive("development")) {
        runtimeClasspath {
            extendsFrom(developmentOnly)
        }
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation(platform(SpringBootPlugin.BOM_COORDINATES))
    implementation(platform("org.testcontainers:testcontainers-bom:${DependencyVersions.testcontainers}"))

    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("org.springframework.boot:spring-boot-starter-cloud-connectors")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("com.github.ben-manes.caffeine:caffeine")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("de.vandermeer:asciitable:${DependencyVersions.asciiTable}")
    implementation("io.dropwizard.metrics:metrics-servlets")
    implementation("org.apache.httpcomponents:httpclient")
    implementation("org.jsoup:jsoup:${DependencyVersions.jsoup}")
    implementation("org.postgresql:postgresql")
    implementation("org.springframework.session:spring-session-jdbc")

    if (isProfileActive("development")) {
        developmentOnly("org.springframework.boot:spring-boot-devtools")
    }

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:postgresql")
}

spotless {
    kotlin {
        licenseHeaderFile(rootProject.file("config/spotless/license.kt"))
    }
}

jib {
    from {
        image = "adoptopenjdk:${DependencyVersions.adoptOpenJdk}"
    }
}
