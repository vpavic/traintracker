plugins {
	java
	alias(libs.plugins.spring.boot)
	alias(libs.plugins.jib)
	alias(libs.plugins.spotless)
}

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(17))
	}
}

repositories {
	mavenCentral()
	maven {
		url = uri("https://repo.spring.io/milestone/")
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

	runtimeOnly(libs.jdbc.postgresql)

	annotationProcessor(platform(libs.spring.boot.dependencies))
	annotationProcessor(libs.spring.boot.configuration.processor)
	annotationProcessor(libs.spring.framework.context.indexer)

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
	kotlinGradle {
		indentWithTabs()
	}
}

jib {
	from {
		image = "azul/zulu-openjdk:17.0.9-jre@sha256:d7b1f13177b1fee8c283fff31579941aa0a9bc727fe1600e418c137c4ade8deb"
	}
}
