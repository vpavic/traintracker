/*
 * Copyright 2019 the original author or authors.
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

package io.traintracker

import com.zaxxer.hikari.HikariDataSource
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.test.context.junit4.SpringRunner
import org.testcontainers.containers.PostgreSQLContainer

@RunWith(SpringRunner::class)
@SpringBootTest
class TrainTrackerApplicationTests {
    @Test
    fun contextLoads() {
    }

    @TestConfiguration
    internal class Config {
        @Bean
        fun postgreSqlContainer(): KPostgreSQLContainer {
            val postgreSqlContainer = KPostgreSQLContainer("postgres:11.4")
            postgreSqlContainer.start()
            return postgreSqlContainer
        }

        @Bean
        fun dataSource(postgreSqlContainer: KPostgreSQLContainer): HikariDataSource {
            val dataSource = HikariDataSource()
            dataSource.jdbcUrl = postgreSqlContainer.getJdbcUrl()
            dataSource.username = postgreSqlContainer.getUsername()
            dataSource.password = postgreSqlContainer.getPassword()
            return dataSource
        }
    }

    class KPostgreSQLContainer(dockerImageName: String) : PostgreSQLContainer<KPostgreSQLContainer>(dockerImageName)
}
