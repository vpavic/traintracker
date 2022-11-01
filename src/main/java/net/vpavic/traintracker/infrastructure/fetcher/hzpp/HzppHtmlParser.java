package net.vpavic.traintracker.infrastructure.fetcher.hzpp;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import net.vpavic.traintracker.domain.model.agency.Agencies;
import net.vpavic.traintracker.domain.model.voyage.Station;
import net.vpavic.traintracker.domain.model.voyage.Voyage;
import net.vpavic.traintracker.domain.model.voyage.VoyageId;

final class HzppHtmlParser {

	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yy' u 'kk:mm");

	private HzppHtmlParser() {
	}

	static Optional<Voyage> parseVoyage(String html) {
		Document doc = Jsoup.parse(html);
		Element form = doc.child(0).child(1).child(2);
		Element currentPositionTable = form.child(1).child(0);
		if (!"tbody".equals(currentPositionTable.tag().getName())) {
			return Optional.empty();
		}
		Elements currentPositionRows = currentPositionTable.children();
		String voyageId = currentPositionRows.get(0).child(0).textNodes().get(0).text().trim();
		String name = currentPositionRows.get(1).child(0).child(1).text().trim().replace('+', ' ');
		Element position = currentPositionRows.get(2).child(0);
		String direction = position.child(0).text().trim();
		String timeRaw = position.child(1).text().trim();
		String delayRaw = currentPositionRows.get(3).child(0).child(0).child(0).text().trim();
		LocalTime time = LocalTime.parse(timeRaw.substring(12, 17));
		int delay = delayRaw.startsWith("Kasni") ? Integer.parseInt(delayRaw.substring(6, delayRaw.indexOf(' ', 6)))
				: 0;
		Station station;
		if (direction.equals("Odlazak")) {
			station = new Station(name);
			station.setDepartureTime(time);
			station.setDepartureDelay(delay);
		}
		else if (direction.equals("Dolazak") || direction.equals("Završio vožnju") || direction.equals("Formiran")) {
			station = new Station(name);
			station.setArrivalTime(time);
			station.setArrivalDelay(delay);
		}
		else {
			throw new IllegalStateException("Unknown direction: " + direction);
		}
		String reportTime = form.child(3).child(0).textNodes().get(0).text().trim().substring(16);
		OffsetDateTime generatedTime = LocalDateTime.parse(reportTime, dateTimeFormatter)
				.atZone(Agencies.hz.getTimezone()).toOffsetDateTime();
		return Optional.of(new Voyage(VoyageId.of(voyageId), List.of(station), generatedTime));
	}

}
