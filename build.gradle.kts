import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("application")
    id("checkstyle")
    id("com.diffplug.spotless")
    id("com.github.ben-manes.versions")
    id("com.google.cloud.tools.jib")
    kotlin("jvm")
    kotlin("plugin.spring")
}

repositories {
    mavenCentral()
    maven(url = "https://repo.spring.io/libs-milestone/")
}

dependencies {
    implementation(platform("org.springframework.boot:spring-boot-dependencies:2.5.0"))
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.github.ben-manes.caffeine:caffeine")
    implementation("de.vandermeer:asciitable:0.3.2")
    implementation("org.apache.httpcomponents:httpclient")
    implementation("org.jsoup:jsoup:1.13.1")
    implementation("org.flywaydb:flyway-core")
    implementation("org.postgresql:postgresql")
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.session:spring-session-jdbc")

    testImplementation(platform("org.testcontainers:testcontainers-bom:1.15.3"))
    testImplementation("io.mockk:mockk:1.11.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.mockito")
    }
    testImplementation("org.testcontainers:postgresql")
    testImplementation("org.testcontainers:testcontainers")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
}

application {
    mainClass.set("io.github.vpavic.traintracker.TrainTrackerApplication")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

spotless {
    java {
        licenseHeaderFile(rootProject.file("config/spotless/license.java"))
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
        image = "adoptopenjdk/openjdk11@sha256:cbd20379d141de45148f6e2f2be388f8cbb3a5211eaaa389930a3a80b56d95ee"
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
}
