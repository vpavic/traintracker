package net.vpavic.traintracker.infrastructure.cache;

import java.time.Duration;
import java.util.Map;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.LoggingCacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

import net.vpavic.traintracker.domain.model.voyage.Voyage;
import net.vpavic.traintracker.infrastructure.json.jackson.TrainTrackerModule;

@Configuration(proxyBeanMethods = false)
@EnableCaching
class CacheConfiguration implements CachingConfigurer {

	@Override
	public CacheErrorHandler errorHandler() {
		return new LoggingCacheErrorHandler(LogFactory.getLog(LoggingCacheErrorHandler.class), true);
	}

	@Bean
	RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
		return builder -> builder
				.withInitialCacheConfigurations(Map.of("voyages", voyageCacheConfiguration()))
				.disableCreateOnMissingCache();
	}

	private static RedisCacheConfiguration voyageCacheConfiguration() {
		return RedisCacheConfiguration.defaultCacheConfig()
				.serializeValuesWith(SerializationPair.fromSerializer(voyageRedisSerializer()))
				.entryTtl(Duration.ofMinutes(1L))
				.computePrefixWith(cacheName -> "traintracker:" + cacheName + ":");
	}

	private static Jackson2JsonRedisSerializer<Voyage> voyageRedisSerializer() {
		JsonMapper jsonMapper = JsonMapper.builder()
				.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
				.disable(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS)
				.addModule(new JavaTimeModule())
				.addModule(new TrainTrackerModule())
				.build();
		return new Jackson2JsonRedisSerializer<>(jsonMapper, Voyage.class);
	}

}
