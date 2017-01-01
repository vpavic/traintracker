package io.traintracker.core;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

@Component
class SiVoyageFetcher implements VoyageFetcher {

	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	private static final ZoneId zoneId = ZoneId.of("Europe/Ljubljana");

	@Override
	public Voyage getVoyage(String train) {
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
			throw new VoyageNotFoundException();
		}

		return new Voyage(stations, doc.location(), zoneId);
	}

}
