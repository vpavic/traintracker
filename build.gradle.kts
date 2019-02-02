plugins {
    java
    checkstyle
    id("com.github.ben-manes.versions") version "0.20.0"
    id("org.springframework.boot") version "2.1.2.RELEASE"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    mavenCentral()
}

val developmentOnly: Configuration by configurations.creating

configurations {
    all {
        resolutionStrategy.force("org.thymeleaf.extras:thymeleaf-extras-java8time:3.0.3.RELEASE")
    }
    runtimeClasspath {
        extendsFrom(configurations["developmentOnly"])
    }
}

dependencies {
    arrayOf("implementation", "annotationProcessor").forEach {
        add(it, platform(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES))
    }
    implementation(platform("org.testcontainers:testcontainers-bom:1.10.6"))

    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("org.springframework.boot:spring-boot-starter-cloud-connectors")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("com.github.ben-manes.caffeine:caffeine")
    implementation("de.vandermeer:asciitable:0.3.2")
    implementation("io.dropwizard.metrics:metrics-servlets")
    implementation("org.apache.httpcomponents:httpclient")
    implementation("org.jsoup:jsoup:1.11.3")
    implementation("org.postgresql:postgresql")
    implementation("org.springframework.session:spring-session-jdbc")

    annotationProcessor("org.springframework:spring-context-indexer")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:postgresql")
}

checkstyle {
    toolVersion = "8.17"
}
