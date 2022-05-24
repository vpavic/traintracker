package io.vpavic.traintracker.infrastructure.fetcher.hzpp;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.time.Clock;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import io.vpavic.traintracker.domain.model.carrier.Carrier;
import io.vpavic.traintracker.domain.model.carrier.Carriers;
import io.vpavic.traintracker.domain.model.voyage.Station;
import io.vpavic.traintracker.domain.model.voyage.Voyage;
import io.vpavic.traintracker.infrastructure.fetcher.VoyageFetcher;

@Component
class HzppVoyageFetcher implements VoyageFetcher {

	private static final Logger logger = LoggerFactory.getLogger(HzppVoyageFetcher.class);

	private static final Clock clock = Clock.system(Carriers.hzpp.getTimeZone());

	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuMMdd");

	private static final HttpResponse.BodyHandler<String> responseBodyHandler = HttpResponse.BodyHandlers.ofString(
			Charset.forName("windows-1250"));

	private final HttpClient httpClient;

	private boolean fetchOverview = false;

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

	@SuppressWarnings("unused")
	void setFetchOverview(boolean fetchOverview) {
		this.fetchOverview = fetchOverview;
	}

	@Override
	public Carrier getCarrier() {
		return Carriers.hzpp;
	}

	@Override
	@Cacheable(cacheNames = "voyages", key = "'hzpp:' + #train")
	public Voyage getVoyage(String train) {
		LocalDateTime now = LocalDateTime.now(clock);
		URI currentPositionRequestUri = buildCurrentPositionRequestUri(train);
		String currentPositionHtml = executeRequest(currentPositionRequestUri);
		if (currentPositionHtml == null) {
			return null;
		}
		Station currentStation = HzppHtmlParser.parseCurrentPosition(currentPositionHtml);
		if (currentStation == null) {
			return null;
		}
		List<Station> stations = List.of();
		if (this.fetchOverview) {
			URI overviewRequestUri = buildOverviewRequestUri(train, now.toLocalDate());
			String overviewHtml = executeRequest(overviewRequestUri);
			if (overviewHtml == null) {
				return null;
			}
			stations = HzppHtmlParser.parseOverview(overviewHtml);
		}
		return new Voyage(Carriers.hzpp.getId(), now.toLocalDate(), currentStation, stations, List.of(),
				now.toLocalTime());
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
			return null;
		}
		catch (InterruptedException ex) {
			logger.warn("Error while executing request: {}", request, ex);
			Thread.currentThread().interrupt();
			return null;
		}
		logger.debug("Received response: {}", response);
		return response.body();
	}

	private static URI buildCurrentPositionRequestUri(String train) {
		String currentPositionUriTemplate = "http://vred.hzinfra.hr/hzinfo/Default.asp" +
				"?vl=%s" +
				"&category=hzinfo" +
				"&service=tpvl" +
				"&screen=2";
		return URI.create(String.format(currentPositionUriTemplate, train));
	}

	private static URI buildOverviewRequestUri(String train, LocalDate date) {
		String overviewUriTemplate = "http://najava.hzinfra.hr/hzinfo/default.asp" +
				"?vl=%s" +
				"&d1=%s" +
				"&category=korisnici" +
				"&service=pkvl" +
				"&screen=2";
		return URI.create(String.format(overviewUriTemplate, train, date.format(formatter)));
	}

}
