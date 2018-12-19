/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
            PostgreSQLContainer postgreSqlContainer = new PostgreSQLContainer("postgres:10.6");
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
