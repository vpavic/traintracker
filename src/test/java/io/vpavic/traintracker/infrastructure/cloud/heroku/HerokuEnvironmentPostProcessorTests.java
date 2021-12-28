package io.vpavic.traintracker.infrastructure.cloud.heroku;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.support.TestPropertySourceUtils;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link HerokuEnvironmentPostProcessor}.
 */
class HerokuEnvironmentPostProcessorTests {

    private final HerokuEnvironmentPostProcessor environmentPostProcessor = new HerokuEnvironmentPostProcessor();

    private final ConfigurableApplicationContext context = new AnnotationConfigApplicationContext();

    @BeforeEach
    void setUp() {
        TestPropertySourceUtils.addInlinedPropertiesToEnvironment(this.context, "DYNO=test");
    }

    @Test
    void postProcessEnvironment_WithPort_ShouldSetServerPort() {
        TestPropertySourceUtils.addInlinedPropertiesToEnvironment(this.context, "PORT=8080");
        this.environmentPostProcessor.postProcessEnvironment(this.context.getEnvironment(), null);
        assertThat(getProperty("server.port")).isEqualTo("8080");
    }

    @Test
    void postProcessEnvironment_WithDatabaseUrl_ShouldSetSpringDatasourceUrl() {
        TestPropertySourceUtils.addInlinedPropertiesToEnvironment(this.context,
                "DATABASE_URL=postgres://username:password@example.com:5432/test");
        this.environmentPostProcessor.postProcessEnvironment(this.context.getEnvironment(), null);
        assertThat(getProperty("spring.datasource.url"))
                .isEqualTo("jdbc:postgresql://username:password@example.com:5432/test");
    }

    @Test
    void postProcessEnvironment_WithRedisUrl_ShouldSetSpringRedisUrl() {
        TestPropertySourceUtils.addInlinedPropertiesToEnvironment(this.context,
                "REDIS_URL=redis://:password@example.com:6379");
        this.environmentPostProcessor.postProcessEnvironment(this.context.getEnvironment(), null);
        assertThat(getProperty("spring.redis.url")).isEqualTo("redis://:password@example.com:6379");
    }

    private String getProperty(String key) {
        return this.context.getEnvironment().getProperty(key);
    }

}
