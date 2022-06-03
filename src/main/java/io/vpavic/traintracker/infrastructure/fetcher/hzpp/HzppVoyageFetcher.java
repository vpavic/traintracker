package io.vpavic.traintracker.infrastructure.fetcher.hzpp;

import java.io.Closeable;
import java.io.IOException;
import java.net.URI;
import java.util.Objects;
import java.util.Optional;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import io.vpavic.traintracker.domain.model.carrier.Carrier;
import io.vpavic.traintracker.domain.model.carrier.Carriers;
import io.vpavic.traintracker.domain.model.voyage.Voyage;
import io.vpavic.traintracker.domain.model.voyage.VoyageId;
import io.vpavic.traintracker.infrastructure.fetcher.VoyageFetcher;

@Component
class HzppVoyageFetcher implements VoyageFetcher, Closeable {

	private static final Logger logger = LoggerFactory.getLogger(HzppVoyageFetcher.class);

	private final CloseableHttpClient httpClient;

	HzppVoyageFetcher(CloseableHttpClient hzppHttpClient) {
		Objects.requireNonNull(hzppHttpClient, "hzppHttpClient must not be null");
		this.httpClient = hzppHttpClient;
	}

	@Override
	public Carrier getCarrier() {
		return Carriers.hzpp;
	}

	@Override
	@Cacheable(cacheNames = "voyages", key = "'hzpp:' + #voyageId")
	public Optional<Voyage> getVoyage(VoyageId voyageId) {
		URI currentPositionRequestUri = buildCurrentPositionRequestUri(voyageId);
		String currentPositionHtml = executeRequest(currentPositionRequestUri);
		if (currentPositionHtml == null) {
			return Optional.empty();
		}
		return HzppHtmlParser.parseVoyage(currentPositionHtml);
	}

	private String executeRequest(URI uri) {
		HttpGet request = new HttpGet(uri);
		logger.debug("Executing request: {}", request);
		try (CloseableHttpResponse response = this.httpClient.execute(request)) {
			logger.debug("Received response: {}", response);
			return EntityUtils.toString(response.getEntity(), "windows-1250");
		}
		catch (IOException ex) {
			logger.warn("Error while executing request: {}", request, ex);
			throw new IllegalStateException(ex.getMessage(), ex);
		}
	}

	private static URI buildCurrentPositionRequestUri(VoyageId voyageId) {
		String currentPositionUriTemplate = "http://vred.hzinfra.hr/hzinfo/Default.asp" +
				"?vl=%s" +
				"&category=hzinfo" +
				"&service=tpvl" +
				"&screen=2";
		return URI.create(String.format(currentPositionUriTemplate, voyageId));
	}

	@Override
	public void close() throws IOException {
		this.httpClient.close();
	}

}
