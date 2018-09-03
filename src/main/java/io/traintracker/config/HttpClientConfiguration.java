package io.traintracker.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpClientConfiguration {

	@Bean
	public CloseableHttpClient httpClient() {
		// @formatter:off
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectionRequestTimeout(5000)
				.setConnectTimeout(5000)
				.setSocketTimeout(5000)
				.build();
		// @formatter:on

		// @formatter:off
		return HttpClientBuilder.create()
				.setDefaultRequestConfig(requestConfig)
				.useSystemProperties()
				.build();
		// @formatter:on
	}

}
