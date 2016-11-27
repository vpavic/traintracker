package io.traintracker.app.infrastructure;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.traintracker.app.domain.VoyageFetcher;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import static java.util.Objects.requireNonNull;

abstract class AbstractVoyageFetcher implements VoyageFetcher, AutoCloseable {

	private final CloseableHttpClient httpClient;

	protected AbstractVoyageFetcher(CloseableHttpClient httpClient) {
		this.httpClient = requireNonNull(httpClient);
	}

	protected Matcher getMatcher(URI uri, Charset charset, Pattern pattern) throws IOException {
		HttpGet httpGet = new HttpGet(uri);

		try (CloseableHttpResponse httpResponse = this.httpClient.execute(httpGet)) {
			HttpEntity httpResponseEntity = httpResponse.getEntity();
			String html = EntityUtils.toString(httpResponseEntity, charset);

			return pattern.matcher(html);
		}
	}

	@Override
	public void close() throws IOException {
		this.httpClient.close();
	}

}