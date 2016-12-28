package io.traintracker.config;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpClientConfiguration {

	@Bean
	public CloseableHttpClient httpClient() {
		return HttpClients.createDefault();
	}

}
