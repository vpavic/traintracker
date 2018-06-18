package io.traintracker.core;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Deque;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
class HrVoyageFetcher implements VoyageFetcher {

	private static final Carrier carrier = new Carrier("hr", "HŽ Infrastruktura",
			"http://www.hzinfra.hr/", ZoneId.of("Europe/Zagreb"));

	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuMMdd");

	@Override
	public String getCountry() {
		return carrier.getId();
	}

	@Override
	@Cacheable("voyages-hr")
	public Voyage getVoyage(String train) {
		Document doc;

		try {
			doc = Jsoup.connect("http://najava.hzinfra.hr/hzinfo/default.asp")
					.data("vl", train)
					.data("d1", LocalDate.now(carrier.getTimezone()).format(formatter))
					.data("category", "korisnici")
					.data("service", "pkvl")
					.data("screen", "2")
					.timeout(10000)
					.get();
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}

		Deque<Station> stations = HrDocumentParser.parse(doc);

		if (stations.isEmpty()) {
			return null;
		}

		return new Voyage(stations, carrier, doc.location());
	}

}
