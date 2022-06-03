package io.vpavic.traintracker.infrastructure.fetcher.hzpp;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.time.Duration;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executors;

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
class HzppVoyageFetcher implements VoyageFetcher {

	private static final Logger logger = LoggerFactory.getLogger(HzppVoyageFetcher.class);

	private static final HttpResponse.BodyHandler<String> responseBodyHandler = HttpResponse.BodyHandlers.ofString(
			Charset.forName("windows-1250"));

	private final HttpClient httpClient;

	HzppVoyageFetcher(HttpClient httpClient) {
		Objects.requireNonNull(httpClient, "httpClient must not be null");
		this.httpClient = httpClient;
	}

	HzppVoyageFetcher() {
		this(defaultHttpClient());
	}

	private static HttpClient defaultHttpClient() {
		return HttpClient.newBuilder()
				.connectTimeout(Duration.ofSeconds(10L))
				.executor(Executors.newFixedThreadPool(5))
				.build();
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
		HttpRequest request = HttpRequest.newBuilder()
				.uri(uri)
				.build();
		logger.debug("Executing request: {}", request);
		HttpResponse<String> response;
		try {
			response = this.httpClient.send(request, responseBodyHandler);
		}
		catch (IOException ex) {
			logger.warn("Error while executing request: {}", request, ex);
			throw new IllegalStateException(ex.getMessage(), ex);
		}
		catch (InterruptedException ex) {
			logger.warn("Error while executing request: {}", request, ex);
			Thread.currentThread().interrupt();
			throw new IllegalStateException(ex.getMessage(), ex);
		}
		logger.debug("Received response: {}", response);
		return response.body();
	}

	private static URI buildCurrentPositionRequestUri(VoyageId voyageId) {
		String currentPositionUriTemplate = "http://vred.hzinfra.hr/hzinfo/Default.asp" +
				"?vl=%s" +
				"&category=hzinfo" +
				"&service=tpvl" +
				"&screen=2";
		return URI.create(String.format(currentPositionUriTemplate, voyageId));
	}

}
