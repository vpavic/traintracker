plugins {
    java
    checkstyle
    id("com.github.ben-manes.versions") version "0.20.0"
    id("com.gorylenko.gradle-git-properties") version "1.5.2"
    id("io.spring.dependency-management") version "1.0.6.RELEASE"
    id("org.springframework.boot") version "2.1.0.RC1"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    mavenCentral()
    maven(url = "https://repo.spring.io/libs-milestone/")
}

dependencyManagement {
    imports {
        mavenBom("org.testcontainers:testcontainers-bom:1.9.1")
    }
}

dependencies {
    compile("org.springframework.boot:spring-boot-starter-actuator")
    compile("org.springframework.boot:spring-boot-starter-cache")
    compile("org.springframework.boot:spring-boot-starter-cloud-connectors")
    compile("org.springframework.boot:spring-boot-starter-data-redis")
    compile("org.springframework.boot:spring-boot-starter-jdbc")
    compile("org.springframework.boot:spring-boot-starter-security")
    compile("org.springframework.boot:spring-boot-starter-thymeleaf")
    compile("org.springframework.boot:spring-boot-starter-web")

    compile("org.springframework.boot:spring-boot-devtools")
    compile("org.springframework.security:spring-security-oauth2-client")
    compile("org.springframework.security:spring-security-oauth2-jose")
    compile("org.springframework.session:spring-session-data-redis:2.1.0.RELEASE")

    compile("de.vandermeer:asciitable:0.3.2")
    compile("org.apache.httpcomponents:httpclient")
    compile("org.jsoup:jsoup:1.11.3")
    compile("org.postgresql:postgresql")

    testCompile("org.springframework.boot:spring-boot-starter-test")
    testCompile("org.testcontainers:postgresql")
    testCompile("org.testcontainers:testcontainers")
}

checkstyle {
    toolVersion = "8.14"
}
