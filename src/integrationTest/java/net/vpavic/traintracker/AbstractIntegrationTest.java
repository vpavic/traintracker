package net.vpavic.traintracker;

import java.io.File;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.ComposeContainer;

public abstract class AbstractIntegrationTest {

	private static final ComposeContainer compose = new ComposeContainer(new File("compose.yaml"))
			.withServices("postgresql", "redis")
			.withExposedService("postgresql", 5432)
			.withExposedService("redis", 6379)
			.withEnv("TRAINTRACKER_POSTGRESQL_PORT", "0")
			.withEnv("TRAINTRACKER_REDIS_PORT", "0")
			.withLocalCompose(true);

	static {
		compose.start();
	}

	@DynamicPropertySource
	static void dataSourceProperties(DynamicPropertyRegistry registry) {
		compose.getContainerByServiceName("postgresql").ifPresent((postgresql) -> {
			registry.add("spring.datasource.url", () -> String.format("jdbc:postgresql://%s:%d/traintracker",
					postgresql.getHost(), postgresql.getFirstMappedPort()));
			registry.add("spring.datasource.username", () -> "traintracker");
			registry.add("spring.datasource.password", () -> "traintracker");
		});
		compose.getContainerByServiceName("redis").ifPresent((redis) -> {
			registry.add("spring.redis.host", redis::getHost);
			registry.add("spring.redis.port", redis::getFirstMappedPort);
		});
	}

}
