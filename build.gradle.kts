buildscript {
	dependencies {
		classpath(libs.jib.layer.filter)
	}
}

plugins {
	application
	alias(libs.plugins.jib)
	alias(libs.plugins.spotless)
}

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(17))
	}
}

tasks.withType<JavaCompile>().configureEach {
	options.compilerArgs.add("-parameters")
}

application {
	mainClass.set("net.vpavic.traintracker.TrainTrackerApplication")
}

repositories {
	mavenCentral()
	maven {
		url = uri("https://repo.spring.io/milestone/")
	}
}

val developmentOnly by configurations.creating

configurations {
	runtimeClasspath {
		extendsFrom(developmentOnly)
	}
}

dependencies {
	modules {
		module("io.lettuce:lettuce-core") {
			replacedBy("redis.clients:jedis")
		}
		module("org.json:json") {
			replacedBy("com.vaadin.external.google:android-json")
		}
	}

	implementation(platform(libs.spring.boot.dependencies))
	implementation(libs.commons.lang)
	implementation(libs.flyway)
	implementation(libs.jedis)
	implementation(libs.jsoup)
	implementation(libs.spring.boot.starter.cache)
	implementation(libs.spring.boot.starter.data.redis)
	implementation(libs.spring.boot.starter.jdbc)
	implementation(libs.spring.boot.starter.thymeleaf)
	implementation(libs.spring.boot.starter.web)
	implementation(libs.spring.session.data.redis)
	implementation(libs.webjars.bootstrap)
	implementation(libs.webjars.flagicons)
	implementation(libs.webjars.htmx)

	compileOnly(libs.spring.boot.devtools)

	runtimeOnly(platform(libs.spring.boot.dependencies))
	runtimeOnly(libs.jdbc.postgresql)

	annotationProcessor(platform(libs.spring.boot.dependencies))
	annotationProcessor(libs.spring.framework.context.indexer)

	developmentOnly(platform(libs.spring.boot.dependencies))
	developmentOnly(libs.spring.boot.devtools)
	developmentOnly(libs.webjars.livereload)
}

testing {
	suites {
		val test by getting(JvmTestSuite::class) {
			useJUnitJupiter()
			dependencies {
				implementation(libs.archunit.junit5)
				implementation(libs.spring.boot.starter.test)
				implementation(libs.spring.boot.devtools)
			}
		}
	}
}

spotless {
	java {
		indentWithTabs()
		importOrder("java", "javax", "", "net.vpavic", "\\#")
	}
	groovyGradle {
		indentWithTabs()
	}
}

jib {
	from {
		image = "azul/zulu-openjdk:17.0.9-jre@sha256:d7b1f13177b1fee8c283fff31579941aa0a9bc727fe1600e418c137c4ade8deb"
	}
	pluginExtensions {
		pluginExtension {
			implementation = "com.google.cloud.tools.jib.gradle.extension.layerfilter.JibLayerFilterExtension"
			configuration(Action<com.google.cloud.tools.jib.gradle.extension.layerfilter.Configuration> {
				filters {
					filter {
						glob = "**/spring-boot-devtools-*.jar"
					}
				}
			})
		}
	}
}
