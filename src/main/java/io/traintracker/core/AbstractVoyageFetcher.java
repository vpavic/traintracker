package io.traintracker.core;

import java.net.URI;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import static java.util.Objects.requireNonNull;

abstract class AbstractVoyageFetcher implements VoyageFetcher {

	private static final int DEFAULT_TIMEOUT_SECONDS = 30;

	private final CloseableHttpClient httpClient;

	protected AbstractVoyageFetcher(CloseableHttpClient httpClient) {
		this.httpClient = requireNonNull(httpClient);
	}

	protected String loadHtml(URI uri, Charset charset) {
		RequestConfig config = RequestConfig.custom()
				.setConnectionRequestTimeout(DEFAULT_TIMEOUT_SECONDS * 1000)
				.setConnectTimeout(DEFAULT_TIMEOUT_SECONDS * 1000)
				.setSocketTimeout(DEFAULT_TIMEOUT_SECONDS * 1000)
				.build();

		HttpGet request = new HttpGet(uri);
		request.setConfig(config);

		try (CloseableHttpResponse httpResponse = this.httpClient.execute(request)) {
			HttpEntity httpResponseEntity = httpResponse.getEntity();
			return EntityUtils.toString(httpResponseEntity, charset);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
