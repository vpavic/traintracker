package ws.traintracker.core;

import java.time.LocalTime;
import java.util.ArrayDeque;
import java.util.Deque;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

final class HrDocumentParser {

	private HrDocumentParser() {
	}

	static Deque<Station> parse(Document doc) {
		Deque<Station> stations = new ArrayDeque<>();

		Elements tables = doc.getElementsByTag("tbody");

		if (tables.size() == 3) {
			Elements rows = tables.last().children();
			rows.remove(0);

			for (Element row : rows) {
				String name = row.child(0).text().trim();
				String direction = row.child(1).text().trim();
				String time = row.child(3).text().trim();
				String delay = row.child(4).text().trim();

				Station station;

				if (direction.equals("Dolazak")) {
					station = new Station(name);
					station.setArrivalTime(LocalTime.parse(time));
					station.setArrivalDelay(delay.isEmpty() ? 0 : Integer.parseInt(delay));
				}
				else {
					if (!stations.isEmpty() && stations.peekLast().getName().equals(name)) {
						station = stations.removeLast();
					}
					else {
						station = new Station(name);
					}

					station.setDepartureTime(LocalTime.parse(time));
					station.setDepartureDelay(delay.isEmpty() ? 0 : Integer.parseInt(delay));
				}

				stations.add(station);
			}
		}

		return stations;
	}

}
