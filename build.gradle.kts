plugins {
	java
	alias(libs.plugins.spring.boot)
	alias(libs.plugins.spotless)
}

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(21))
	}
}

tasks.withType<JavaCompile>().configureEach {
	options.compilerArgs.addAll(listOf(
			"-Werror",
			"-Xlint:deprecation",
			"-Xlint:rawtypes",
			"-Xlint:unchecked",
			"-Xlint:varargs"
	))
}

repositories {
	mavenCentral()
}

dependencies {
	modules {
		module("io.lettuce:lettuce-core") {
			replacedBy("redis.clients:jedis")
		}
	}

	implementation(platform(libs.spring.boot.dependencies))
	implementation(libs.commons.lang)
	implementation(libs.flyway)
	implementation(libs.jedis)
	implementation(libs.jsoup)
	implementation(libs.micrometer.tracing.bridge.otel)
	implementation(libs.otel.exporter.otlp)
	implementation(libs.spring.boot.starter.actuator)
	implementation(libs.spring.boot.starter.cache)
	implementation(libs.spring.boot.starter.data.redis)
	implementation(libs.spring.boot.starter.jdbc)
	implementation(libs.spring.boot.starter.thymeleaf)
	implementation(libs.spring.boot.starter.web)
	implementation(libs.spring.session.data.redis)
	implementation(libs.webjars.bootstrap)
	implementation(libs.webjars.flagicons)
	implementation(libs.webjars.htmx)
	implementation(libs.webjars.locator.lite)

	compileOnly(libs.spring.boot.devtools)

	runtimeOnly(libs.jdbc.postgresql)

	annotationProcessor(platform(libs.spring.boot.dependencies))
	annotationProcessor(libs.spring.boot.configuration.processor)

	developmentOnly(platform(libs.spring.boot.dependencies))
	developmentOnly(libs.spring.boot.devtools)
	developmentOnly(libs.webjars.livereload)
}

testing {
	@Suppress("UnstableApiUsage")
	suites {
		val test by getting(JvmTestSuite::class) {
			useJUnitJupiter()
			dependencies {
				implementation(libs.archunit.junit5)
				implementation(libs.spring.boot.starter.test)
			}
		}
		register<JvmTestSuite>("integrationTest") {
			dependencies {
				implementation(project())
				implementation(project.configurations["compileClasspath"])
				implementation(platform(libs.spring.boot.dependencies))
				implementation(libs.spring.boot.starter.test)
				implementation(libs.testcontainers.junit.jupiter)
			}
			targets {
				all {
					testTask.configure {
						shouldRunAfter(test)
					}
				}
			}
		}
	}
}

spotless {
	java {
		indentWithTabs()
		importOrder("java", "javax", "", "net.vpavic", "\\#")
	}
	kotlinGradle {
		indentWithTabs()
	}
}
