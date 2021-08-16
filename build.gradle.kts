import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import com.google.cloud.tools.jib.gradle.JibTask
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

application {
    mainClass.set("io.github.vpavic.traintracker.TrainTrackerApplication")
}

repositories {
    mavenCentral()
    maven(url = "https://repo.spring.io/libs-milestone/")
}

val javaAgent by configurations.creating

dependencies {
    implementation(platform("org.springframework.boot:spring-boot-dependencies:2.5.3"))
    implementation(platform("org.jetbrains.kotlin:kotlin-bom:${project.property("kotlinVersion")}"))
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.github.ben-manes.caffeine:caffeine")
    implementation("de.vandermeer:asciitable:0.3.2")
    implementation("org.apache.httpcomponents:httpclient")
    implementation("org.jsoup:jsoup:1.14.2")
    implementation("org.flywaydb:flyway-core")
    implementation("org.postgresql:postgresql")
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.session:spring-session-jdbc")

    javaAgent("com.newrelic.agent.java:newrelic-agent:7.1.1")

    testImplementation(platform("org.testcontainers:testcontainers-bom:1.16.0"))
    testImplementation("com.tngtech.archunit:archunit-junit5:0.20.1")
    testImplementation("io.mockk:mockk:1.12.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.mockito")
    }
    testImplementation("org.testcontainers:postgresql")
    testImplementation("org.testcontainers:testcontainers")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        allWarningsAsErrors = true
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
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

val processJavaAgents by tasks.register<Copy>("processJavaAgents") {
    from(javaAgent)
    into(layout.buildDirectory.dir("java-agents"))
    rename { s -> s.substring(0, s.lastIndexOf("-")) + s.substring(s.lastIndexOf(".")) }
}

tasks.withType<JibTask> {
    dependsOn(processJavaAgents)
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

jib {
    from {
        // azul/zulu-openjdk-alpine:11.0.12-jre
        image = "azul/zulu-openjdk-alpine@sha256:677749d13e8efd2ec5145ce2c9424abbf80dc54b05bf4a2240f896fff19476ee"
    }
    extraDirectories {
        setPaths(processJavaAgents.outputs)
    }
}
