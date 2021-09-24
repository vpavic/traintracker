package io.github.vpavic.traintracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.jdbc.PostgreSqlJdbcIndexedSessionRepositoryCustomizer;

@Configuration(proxyBeanMethods = false)
class SessionConfiguration {

    @Bean
    PostgreSqlJdbcIndexedSessionRepositoryCustomizer sessionRepositoryCustomizer() {
        return new PostgreSqlJdbcIndexedSessionRepositoryCustomizer();
    }

}
