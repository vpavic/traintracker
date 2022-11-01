package net.vpavic.traintracker.infrastructure.session;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

@Configuration(proxyBeanMethods = false)
class SessionConfiguration {

	@Bean
	GenericJackson2JsonRedisSerializer springSessionDefaultRedisSerializer() {
		return new GenericJackson2JsonRedisSerializer(JsonMapper.builder()
				.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
				.disable(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS)
				.addModule(new JavaTimeModule())
				.build());
	}

}
