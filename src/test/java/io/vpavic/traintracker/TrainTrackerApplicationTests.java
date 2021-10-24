package io.vpavic.traintracker;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.session.jdbc.JdbcIndexedSessionRepository;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class TrainTrackerApplicationTests {

    @Test
    void contextLoads(ApplicationContext applicationContext) {
        assertThat(applicationContext).isNotNull();
        assertThat(applicationContext.getBeanNamesForType(CaffeineCacheManager.class)).isNotEmpty();
        assertThat(applicationContext.getBeanNamesForType(JdbcIndexedSessionRepository.class)).isNotEmpty();
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
