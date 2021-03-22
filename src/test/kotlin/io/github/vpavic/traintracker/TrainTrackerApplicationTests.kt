/*
 * Copyright 2021 the original author or authors.
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

package io.github.vpavic.traintracker

import com.zaxxer.hikari.HikariDataSource
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.test.context.ActiveProfiles
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

@SpringBootTest
@ActiveProfiles("test")
class TrainTrackerApplicationTests {

    @Test
    fun `Assert that application context loads`(applicationContext: ApplicationContext) {
        assertThat(applicationContext).isNotNull
    }

    @TestConfiguration
    internal class Config {

        @Bean
        fun postgreSqlContainer(): PostgreSQLContainer<*> {
            val postgreSqlContainer: PostgreSQLContainer<*> =
                PostgreSQLContainer<Nothing>(DockerImageName.parse("postgres:11.5"))
            postgreSqlContainer.start()
            return postgreSqlContainer
        }

        @Bean
        fun dataSource(postgreSqlContainer: PostgreSQLContainer<*>): HikariDataSource {
            val dataSource = HikariDataSource()
            dataSource.jdbcUrl = postgreSqlContainer.jdbcUrl
            dataSource.username = postgreSqlContainer.username
            dataSource.password = postgreSqlContainer.password
            return dataSource
        }

        @Bean
        fun redisContainer(): GenericContainer<*> {
            val redisContainer: GenericContainer<*> =
                GenericContainer<Nothing>(DockerImageName.parse("redis:5.0.7")).withExposedPorts(6379)
            redisContainer.start()
            return redisContainer
        }

        @Bean
        fun redisConnectionFactory(redisContainer: GenericContainer<*>): JedisConnectionFactory {
            val redisConfiguration =
                RedisStandaloneConfiguration(redisContainer.containerIpAddress, redisContainer.firstMappedPort)
            return JedisConnectionFactory(redisConfiguration)
        }
    }
}
