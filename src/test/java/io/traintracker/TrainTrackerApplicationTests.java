package io.traintracker;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrainTrackerApplicationTests {

	@Test
	public void contextLoads() {
	}

	@TestConfiguration
	static class Config {

		@Bean
		public PostgreSQLContainer postgreSqlContainer() {
			PostgreSQLContainer postgreSqlContainer = new PostgreSQLContainer("postgres:10.5");
			postgreSqlContainer.start();
			return postgreSqlContainer;
		}

		@Bean
		public HikariDataSource dataSource() {
			HikariDataSource dataSource = new HikariDataSource();
			dataSource.setJdbcUrl(postgreSqlContainer().getJdbcUrl());
			dataSource.setUsername(postgreSqlContainer().getUsername());
			dataSource.setPassword(postgreSqlContainer().getPassword());
			return dataSource;
		}

		@Bean
		public GenericContainer redisContainer() {
			GenericContainer redisContainer = new GenericContainer("redis:4.0.11").withExposedPorts(6379);
			redisContainer.start();
			return redisContainer;
		}

		@Bean
		public LettuceConnectionFactory redisConnectionFactory() {
			RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(
					redisContainer().getContainerIpAddress(), redisContainer().getFirstMappedPort());
			return new LettuceConnectionFactory(configuration);
		}

	}

}
