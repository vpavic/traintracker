package io.traintracker.app.infrastructure;

import java.net.URI;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.traintracker.app.domain.Station;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.stereotype.Component;

@Component
public class HrVoyageFetcher extends AbstractVoyageFetcher {

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("uuMMdd");

	private static final Charset CHARSET = Charset.forName("Cp1250");

	private static final Pattern PATTERN = Pattern.compile("<TD ALIGN=left BGCOLOR=.{7}><FONT FACE=\"Arial\" SIZE=3>(?<name>.*?)</TD>\r\n" +
			"<TD ALIGN=CENTER BGCOLOR=.{7}><FONT FACE=\"Arial\" SIZE=3>(?<direction>.*?)</TD>\r\n" +
			"<TD ALIGN=CENTER BGCOLOR=.{7}><FONT FACE=\"Arial\" SIZE=3>.*?</TD>\r\n" +
			"<TD ALIGN=CENTER BGCOLOR=.{7}><FONT FACE=\"Arial\" SIZE=3>(?<time>.*?)</TD>\r\n" +
			"<TD ALIGN=CENTER BGCOLOR=.{7}><FONT FACE=\"Arial\" SIZE=3>(?<delay>.*?)</TD>");

	public HrVoyageFetcher(CloseableHttpClient httpClient) {
		super(httpClient);
	}

	@Override
	public List<Station> getStations(String train) throws Exception {
		URI uri = new URIBuilder("http://najava.hzinfra.hr/hzinfo/Default.asp")
				.addParameter("VL", train)
				.addParameter("D1", LocalDate.now().format(FORMATTER))
				.addParameter("Category", "korisnici")
				.addParameter("Service", "Pkvl")
				.addParameter("SCREEN", "2")
				.build();

		Matcher matcher = getMatcher(uri, CHARSET, PATTERN);

		Deque<Station> stations = new ArrayDeque<>();

		while (matcher.find()) {
			String name = matcher.group("name").trim();
			String direction = matcher.group("direction").trim();
			String time = matcher.group("time").trim();
			String delay = matcher.group("delay").trim();

			Station station;

			if (direction.equals("Dolazak")) {
				station = new Station(name);
				station.setArrivalTime(LocalTime.parse(time));
				if (!delay.equals("<BR>")) {
					station.setArrivalDelay(Integer.parseInt(delay));
				}
			}
			else {
				if (!stations.isEmpty() && stations.peekLast().getName().equals(name)) {
					station = stations.removeLast();
				}
				else {
					station = new Station(name);
				}

				station.setDepartureTime(LocalTime.parse(time));
				if (!delay.equals("<BR>")) {
					station.setDepartureDelay(Integer.parseInt(delay));
				}
			}

			stations.add(station);
		}

		return new ArrayList<>(stations);
	}

}
