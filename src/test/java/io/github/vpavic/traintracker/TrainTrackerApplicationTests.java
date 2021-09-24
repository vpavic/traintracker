package io.github.vpavic.traintracker;

import com.zaxxer.hikari.HikariDataSource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.session.jdbc.JdbcIndexedSessionRepository;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest
@ActiveProfiles("test")
class TrainTrackerApplicationTests {

    @Test
    void contextLoads(ApplicationContext applicationContext) {
        Assertions.assertThat(applicationContext).isNotNull();
        Assertions.assertThat(applicationContext.getBeanNamesForType(CaffeineCacheManager.class)).isNotEmpty();
        Assertions.assertThat(applicationContext.getBeanNamesForType(JdbcIndexedSessionRepository.class)).isNotEmpty();
    }

    @TestConfiguration
    static class Config {

        @Bean
        PostgreSQLContainer<?> postgreSqlContainer() {
            PostgreSQLContainer<?> postgreSqlContainer = new PostgreSQLContainer<>("postgres:11.5");
            postgreSqlContainer.start();
            return postgreSqlContainer;
        }

        @Bean
        HikariDataSource dataSource(PostgreSQLContainer<?> postgreSqlContainer) {
            HikariDataSource dataSource = new HikariDataSource();
            dataSource.setJdbcUrl(postgreSqlContainer.getJdbcUrl());
            dataSource.setUsername(postgreSqlContainer.getUsername());
            dataSource.setPassword(postgreSqlContainer.getPassword());
            return dataSource;
        }

    }

}
