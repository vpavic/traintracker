package io.traintracker.core;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

final class SiDocumentParser {

	private SiDocumentParser() {
	}

	static Collection<Station> parse(Document doc) {
		List<Station> stations = new ArrayList<>();

		Elements tables = doc.getElementsByTag("tbody");

		if (tables.size() == 1) {
			Elements rows = tables.first().children();

			for (Element row : rows) {
				String name = row.child(0).text().trim();
				String arrivalTime = row.child(1).text().trim();
				String departureTime = row.child(2).text().trim();

				Station station = new Station(name);

				if (!arrivalTime.isEmpty() && !arrivalTime.equals(".")) {
					station.setArrivalTime(LocalTime.parse(arrivalTime));
				}

				if (!departureTime.isEmpty() && !departureTime.equals(".")) {
					station.setDepartureTime(LocalTime.parse(departureTime));
				}

				stations.add(station);
			}
		}

		return stations;
	}

}
