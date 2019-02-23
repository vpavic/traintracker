import org.springframework.boot.gradle.plugin.SpringBootPlugin

plugins {
    java
    kotlin("jvm") version "1.3.21"
    id("com.diffplug.gradle.spotless") version "3.18.0"
    id("com.github.ben-manes.versions") version "0.20.0"
    id("org.jetbrains.kotlin.plugin.spring") version "1.3.21"
    id("org.jlleitschuh.gradle.ktlint") version "7.1.0"
    id("org.springframework.boot") version "2.1.3.RELEASE"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    jcenter()
}

val developmentOnly: Configuration by configurations.creating

configurations {
    runtimeClasspath {
        extendsFrom(configurations["developmentOnly"])
    }
}

dependencies {
    implementation(platform(SpringBootPlugin.BOM_COORDINATES))
    implementation(platform("org.testcontainers:testcontainers-bom:1.10.6"))

    implementation(kotlin("stdlib-jdk8"))

    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("org.springframework.boot:spring-boot-starter-cloud-connectors")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("com.github.ben-manes.caffeine:caffeine")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("de.vandermeer:asciitable:0.3.2")
    implementation("io.dropwizard.metrics:metrics-servlets")
    implementation("org.apache.httpcomponents:httpclient")
    implementation("org.jsoup:jsoup:1.11.3")
    implementation("org.postgresql:postgresql")
    implementation("org.springframework.session:spring-session-jdbc")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:postgresql")
}

spotless {
    kotlin {
        licenseHeaderFile(rootProject.file("config/spotless/license.kt"))
    }
}
