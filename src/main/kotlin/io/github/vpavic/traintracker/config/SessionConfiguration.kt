package io.github.vpavic.traintracker.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.session.jdbc.PostgreSqlJdbcIndexedSessionRepositoryCustomizer

@Configuration(proxyBeanMethods = true)
class SessionConfiguration {

    @Bean
    fun sessionRepositoryCustomizer(): PostgreSqlJdbcIndexedSessionRepositoryCustomizer {
        return PostgreSqlJdbcIndexedSessionRepositoryCustomizer()
    }
}
