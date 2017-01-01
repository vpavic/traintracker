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
class SiVoyageFetcher implements VoyageFetcher {

	private static final String COUNTRY = "si";

	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	private static final ZoneId zoneId = ZoneId.of("Europe/Ljubljana");

	@Override
	public String getCountry() {
		return COUNTRY;
	}

	@Override
	@Cacheable("voyages-si")
	public Optional<Voyage> getVoyage(String train) {
		Document doc;

		try {
			doc = Jsoup.connect("http://www.slo-zeleznice.si/sl/potniki/vozni-redi/zamude")
					.data("view", "train")
					.data("vl", train)
					.data("da", LocalDate.now(zoneId).format(formatter))
					.data("tmpl", "component%")
					.data("loadStyles", "true")
					.timeout(10000)
					.get();
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}

		Collection<Station> stations = SiDocumentParser.parse(doc);

		if (stations.isEmpty()) {
			return Optional.empty();
		}

		return Optional.of(new Voyage(stations, doc.location(), zoneId));
	}

}
