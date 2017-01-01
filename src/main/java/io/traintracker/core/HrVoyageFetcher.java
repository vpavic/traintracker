package io.traintracker.core;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
class HrVoyageFetcher implements VoyageFetcher {

	private static final String COUNTRY = "hr";

	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuMMdd");

	private static final ZoneId zoneId = ZoneId.of("Europe/Zagreb");

	@Override
	public String getCountry() {
		return COUNTRY;
	}

	@Override
	@Cacheable("voyages-hr")
	public Optional<Voyage> getVoyage(String train) {
		Document doc;

		try {
			doc = Jsoup.connect("http://najava.hzinfra.hr/hzinfo/default.asp")
					.data("vl", train)
					.data("d1", LocalDate.now(zoneId).format(formatter))
					.data("category", "korisnici")
					.data("service", "pkvl")
					.data("screen", "2")
					.timeout(10000)
					.get();
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}

		Collection<Station> stations = HrDocumentParser.parse(doc);

		if (stations.isEmpty()) {
			return Optional.empty();
		}

		return Optional.of(new Voyage(stations, doc.location(), zoneId));
	}

}
