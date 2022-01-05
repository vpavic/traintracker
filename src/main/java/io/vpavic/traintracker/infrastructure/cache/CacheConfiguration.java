package io.vpavic.traintracker.infrastructure.cache;

import java.time.Duration;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

import io.vpavic.traintracker.domain.model.voyage.Voyage;
import io.vpavic.traintracker.infrastructure.json.jackson.TrainTrackerModule;

@Configuration(proxyBeanMethods = false)
@EnableCaching
class CacheConfiguration {

	private static final CacheKeyPrefix cacheKeyPrefix = cacheName -> "traintracker:" + cacheName + ":";

	@Bean
	RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
		return RedisCacheManager.builder(redisConnectionFactory)
				.withInitialCacheConfigurations(Map.of("voyages", voyageCacheConfiguration()))
				.disableCreateOnMissingCache()
				.build();
	}

	private static RedisCacheConfiguration voyageCacheConfiguration() {
		return RedisCacheConfiguration.defaultCacheConfig()
				.serializeValuesWith(SerializationPair.fromSerializer(voyageRedisSerializer()))
				.entryTtl(Duration.ofMinutes(1L))
				.computePrefixWith(cacheKeyPrefix);
	}

	private static Jackson2JsonRedisSerializer<Voyage> voyageRedisSerializer() {
		Jackson2JsonRedisSerializer<Voyage> voyageRedisSerializer = new Jackson2JsonRedisSerializer<>(Voyage.class);
		voyageRedisSerializer.setObjectMapper(objectMapper());
		return voyageRedisSerializer;
	}

	private static ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.registerModule(new TrainTrackerModule());
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		objectMapper.disable(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS);
		return objectMapper;
	}

}
