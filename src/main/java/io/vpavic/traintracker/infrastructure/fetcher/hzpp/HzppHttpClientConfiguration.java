package io.vpavic.traintracker.infrastructure.fetcher.hzpp;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
class HzppHttpClientConfiguration {

	@Bean
	CloseableHttpClient hzppHttpClient() {
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectionRequestTimeout(10_000)
				.setConnectTimeout(10_000)
				.setSocketTimeout(10_000)
				.build();
		return HttpClientBuilder.create()
				.setDefaultRequestConfig(requestConfig)
				.useSystemProperties()
				.build();
	}

}
