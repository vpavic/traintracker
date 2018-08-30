package io.traintracker.core;

import java.net.URI;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.jsoup.Jsoup;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;

@Component
class HrVoyageFetcher implements VoyageFetcher {

	private static final Carrier carrier = new Carrier("hr", "HŽ Infrastruktura",
			"http://www.hzinfra.hr/", ZoneId.of("Europe/Zagreb"));

	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuMMdd");

	private static final UriBuilder uriBuilder = new DefaultUriBuilderFactory("http://najava.hzinfra.hr/hzinfo")
			.uriString("/default.asp?vl={train}&d1={date}&category=korisnici&service=pkvl&screen=2");

	private final WebClient webClient;

	HrVoyageFetcher(WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder.baseUrl("http://najava.hzinfra.hr/hzinfo").build();
	}

	@Override
	public String getCountry() {
		return carrier.getId();
	}

	@Override
	@Cacheable("voyages-hr")
	public Voyage getVoyage(String train) {
		URI uri = uriBuilder.build(train, LocalDate.now(carrier.getTimezone()).format(formatter));

		// @formatter:off
		return this.webClient.get()
				.uri(uri)
				.accept(MediaType.TEXT_HTML)
				.exchange()
				.flatMap(response -> response.toEntity(String.class))
				.map(entity -> Jsoup.parse(entity.toString()))
				.map(HrDocumentParser::parse)
				.map(stations -> new Voyage(stations, carrier, uri.toString()))
				.block();
		// @formatter:on
	}

}
