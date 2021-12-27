package io.vpavic.traintracker.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.autoconfigure.session.SessionProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.session.data.redis.RedisSessionRepository;

@Configuration(proxyBeanMethods = false)
@EnableSpringHttpSession
class SessionConfiguration {

    @Bean
    RedisSessionRepository sessionRepository(RedisConnectionFactory redisConnectionFactory,
            SessionProperties sessionProperties) {
        RedisOperations<String, Object> sessionRedisOperations = sessionRedisOperations(redisConnectionFactory);
        RedisSessionRepository sessionRepository = new RedisSessionRepository(sessionRedisOperations);
        sessionRepository.setRedisKeyNamespace("traintracker:sessions");
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        map.from(sessionProperties::getTimeout).to(sessionRepository::setDefaultMaxInactiveInterval);
        return sessionRepository;
    }

    private static RedisOperations<String, Object> sessionRedisOperations(
            RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setDefaultSerializer(new GenericJackson2JsonRedisSerializer(objectMapper()));
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    private static ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.disable(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS);
        return objectMapper;
    }

}
