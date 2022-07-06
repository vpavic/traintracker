package xyz.vpavic.traintracker.infrastructure.cloud.heroku;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.support.TestPropertySourceUtils;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDSoftAssertions.thenSoftly;

/**
 * Tests for {@link HerokuEnvironmentPostProcessor}.
 */
@DisplayName("Heroku environment post-processor")
class HerokuEnvironmentPostProcessorTests {

	private final HerokuEnvironmentPostProcessor environmentPostProcessor = new HerokuEnvironmentPostProcessor();

	private final ConfigurableApplicationContext context = new AnnotationConfigApplicationContext();

	@BeforeEach
	void setUp() {
		TestPropertySourceUtils.addInlinedPropertiesToEnvironment(this.context, "DYNO=test");
	}

	@Test
	@DisplayName("given PORT env variable then sets server.port property")
	void givenPortEnvVarThenSetsServerPortProperty() {
		// given
		TestPropertySourceUtils.addInlinedPropertiesToEnvironment(this.context, "PORT=8080");
		// when
		this.environmentPostProcessor.postProcessEnvironment(this.context.getEnvironment(), null);
		// then
		then(getProperty("server.port")).as("server.port").isEqualTo("8080");
	}

	@Test
	@DisplayName("given DATABASE_URL env variable then sets spring.datasource.url property")
	void givenDatabaseUrlEnvVarThenSetsSpringDatasourceUrlProperty() {
		// given
		TestPropertySourceUtils.addInlinedPropertiesToEnvironment(this.context,
				"DATABASE_URL=postgres://username:password@example.com:5432/test");
		// when
		this.environmentPostProcessor.postProcessEnvironment(this.context.getEnvironment(), null);
		// then
		thenSoftly(softly -> {
			softly.then(getProperty("spring.datasource.url")).as("spring.datasource.url")
					.isEqualTo("jdbc:postgresql://example.com:5432/test");
			softly.then(getProperty("spring.datasource.username")).as("spring.datasource.username")
					.isEqualTo("username");
			softly.then(getProperty("spring.datasource.password")).as("spring.datasource.password")
					.isEqualTo("password");
		});
	}

	@Test
	@DisplayName("given REDIS_URL env variable then sets spring.redis.url property")
	void givenRedisUrlEnvVarThenSetsSpringRedisUrlProperty() {
		// given
		TestPropertySourceUtils.addInlinedPropertiesToEnvironment(this.context,
				"REDIS_URL=redis://:password@example.com:6379");
		// when
		this.environmentPostProcessor.postProcessEnvironment(this.context.getEnvironment(), null);
		// then
		then(getProperty("spring.redis.url")).as("spring.redis.url").isEqualTo("redis://:password@example.com:6379");
	}

	private String getProperty(String key) {
		return this.context.getEnvironment().getProperty(key);
	}

}
