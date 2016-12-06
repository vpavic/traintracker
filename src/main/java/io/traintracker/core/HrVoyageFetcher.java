package io.traintracker.core;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.stereotype.Component;

@Component
class HrVoyageFetcher extends AbstractVoyageFetcher {

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("uuMMdd");

	private static final Charset CHARSET = Charset.forName("Cp1250");

	private static final Pattern PATTERN = Pattern.compile("<TD ALIGN=left BGCOLOR=.{7}><FONT FACE=\"Arial\" SIZE=3>(?<name>.*?)</TD>\r\n" +
			"<TD ALIGN=CENTER BGCOLOR=.{7}><FONT FACE=\"Arial\" SIZE=3>(?<direction>.*?)</TD>\r\n" +
			"<TD ALIGN=CENTER BGCOLOR=.{7}><FONT FACE=\"Arial\" SIZE=3>.*?</TD>\r\n" +
			"<TD ALIGN=CENTER BGCOLOR=.{7}><FONT FACE=\"Arial\" SIZE=3>(?<time>.*?)</TD>\r\n" +
			"<TD ALIGN=CENTER BGCOLOR=.{7}><FONT FACE=\"Arial\" SIZE=3>(?<delay>.*?)</TD>");

	private static final ZoneId ZONE_ID = ZoneId.of("Europe/Zagreb");

	public HrVoyageFetcher(CloseableHttpClient httpClient) {
		super(httpClient);
	}

	@Override
	public Voyage getVoyage(String train) {
		URI uri;

		try {
			uri = new URIBuilder("http://najava.hzinfra.hr/hzinfo/Default.asp")
					.addParameter("VL", train)
					.addParameter("D1", LocalDate.now(ZONE_ID).format(FORMATTER))
					.addParameter("Category", "korisnici")
					.addParameter("Service", "Pkvl")
					.addParameter("SCREEN", "2")
					.build();
		}
		catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}

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
				station.setArrivalDelay(delay.equals("<BR>") ? 0 : Integer.parseInt(delay));
			}
			else {
				if (!stations.isEmpty() && stations.peekLast().getName().equals(name)) {
					station = stations.removeLast();
				}
				else {
					station = new Station(name);
				}

				station.setDepartureTime(LocalTime.parse(time));
				station.setDepartureDelay(delay.equals("<BR>") ? 0 : Integer.parseInt(delay));
			}

			stations.add(station);
		}

		if (stations.isEmpty()) {
			throw new VoyageNotFoundException();
		}

		return new Voyage(stations);
	}

}
