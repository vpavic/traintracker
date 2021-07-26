import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("application")
    id("com.diffplug.spotless")
    id("com.github.ben-manes.versions")
    id("com.google.cloud.tools.jib")
    kotlin("jvm")
    kotlin("plugin.spring")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

repositories {
    mavenCentral()
    maven(url = "https://repo.spring.io/libs-milestone/")
}

dependencies {
    implementation(platform("org.springframework.boot:spring-boot-dependencies:2.5.3"))
    implementation(platform("org.jetbrains.kotlin:kotlin-bom:${project.property("kotlinVersion")}"))
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.github.ben-manes.caffeine:caffeine")
    implementation("de.vandermeer:asciitable:0.3.2")
    implementation("org.apache.httpcomponents:httpclient")
    implementation("org.jsoup:jsoup:1.14.1")
    implementation("org.flywaydb:flyway-core")
    implementation("org.postgresql:postgresql")
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.session:spring-session-jdbc")

    testImplementation(platform("org.testcontainers:testcontainers-bom:1.16.0"))
    testImplementation("com.tngtech.archunit:archunit-junit5:0.20.1")
    testImplementation("io.mockk:mockk:1.12.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.mockito")
    }
    testImplementation("org.testcontainers:postgresql")
    testImplementation("org.testcontainers:testcontainers")
}

application {
    mainClass.set("io.github.vpavic.traintracker.TrainTrackerApplication")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

spotless {
    kotlin {
        licenseHeaderFile(rootProject.file("config/spotless/license.kt"))
        ktlint()
    }
    kotlinGradle {
        ktlint()
    }
}

tasks.named<DependencyUpdatesTask>("dependencyUpdates").configure {
    revision = "release"
    gradleReleaseChannel = "current"
    rejectVersionIf {
        fun isStable(version: String): Boolean {
            val isStableVersion = "^[0-9,.v-]+(-r)?$".toRegex().matches(version)
            val isStableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
            return isStableVersion || isStableKeyword
        }
        isStable(currentVersion) && !isStable(candidate.version)
    }
}

jib {
    from {
        // azul/zulu-openjdk-alpine:11.0.11-jre
        image = "azul/zulu-openjdk-alpine@sha256:0ba1ac6faedc19d2116d6a45880bdf5d0fb7fdbeb543c25549a65444f2f6516e"
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
}
