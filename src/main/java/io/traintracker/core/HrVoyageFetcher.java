package io.traintracker.core;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Deque;
import java.util.Objects;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
class HrVoyageFetcher implements VoyageFetcher {

	private static final Carrier carrier = new Carrier("hr", "HŽ Infrastruktura",
			"http://www.hzinfra.hr/", ZoneId.of("Europe/Zagreb"));

	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuMMdd");

	private final CloseableHttpClient httpClient;

	HrVoyageFetcher(CloseableHttpClient httpClient) {
		Objects.requireNonNull(httpClient, "httpClient must not be null");
		this.httpClient = httpClient;
	}

	@Override
	public String getCountry() {
		return carrier.getId();
	}

	@Override
	@Cacheable("voyages-hr")
	public Voyage getVoyage(String train) {
		URI uri = buildRequestUri(train, LocalDate.now(carrier.getTimezone()));
		CloseableHttpResponse response = executeRequest(uri);
		Document doc = parseDocument(response);
		Deque<Station> stations = HrDocumentParser.parse(doc);

		if (stations.isEmpty()) {
			return null;
		}

		return new Voyage(stations, carrier, uri.toString());
	}

	private CloseableHttpResponse executeRequest(URI uri) {
		try {
			HttpGet request = new HttpGet(uri);
			return this.httpClient.execute(request);
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private Document parseDocument(CloseableHttpResponse response) {
		try {
			String html = EntityUtils.toString(response.getEntity(), "Cp1250");
			return Jsoup.parse(html);
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static URI buildRequestUri(String train, LocalDate date) {
		try {
			// @formatter:off
			return new URIBuilder("http://najava.hzinfra.hr/hzinfo/default.asp")
					.addParameter("vl", train)
					.addParameter("d1", date.format(formatter))
					.addParameter("category", "korisnici")
					.addParameter("service", "pkvl")
					.addParameter("screen", "2")
					.build();
			// @formatter:on
		}
		catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

}
