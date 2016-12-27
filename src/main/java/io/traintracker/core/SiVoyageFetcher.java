package io.traintracker.core;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Strings;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.stereotype.Component;

@Component
class SiVoyageFetcher extends AbstractVoyageFetcher {

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	private static final Charset CHARSET = Charset.forName("Cp1250");

	private static final Pattern PATTERN = Pattern.compile("<tr>\r\n" +
			"                <td class=\"thTitle\">(?<name>.*?)</td>\r\n" +
			"                <td class=\"tdPrice\">(?<arrivalTime>.*?)</td>\r\n" +
			"                <td class=\"tdPrice\">(?<departureTime>.*?)</td>\r\n" +
			"                </tr>");

	private static final ZoneId ZONE_ID = ZoneId.of("Europe/Ljubljana");

	public SiVoyageFetcher(CloseableHttpClient httpClient) {
		super(httpClient);
	}

	@Override
	public Voyage getVoyage(String train) {
		URI uri;

		try {
			uri = new URIBuilder("http://www.slo-zeleznice.si/sl/potniki/vozni-redi/zamude")
					.addParameter("view", "train")
					.addParameter("tmpl", "component%")
					.addParameter("da", LocalDate.now(ZONE_ID).format(FORMATTER))
					.addParameter("vl", train)
					.build();
		}
		catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}

		String html = loadHtml(uri, CHARSET);
		Matcher matcher = PATTERN.matcher(html);

		List<Station> stations = new ArrayList<>();

		while (matcher.find()) {
			String name = matcher.group("name").trim();
			String arrivalTime = matcher.group("arrivalTime").trim();
			String departureTime = matcher.group("departureTime").trim();

			Station station = new Station(name);

			if (!Strings.isNullOrEmpty(arrivalTime) && !arrivalTime.equals(".")) {
				station.setArrivalTime(LocalTime.parse(arrivalTime));
			}

			if (!Strings.isNullOrEmpty(departureTime) && !departureTime.equals(".")) {
				station.setDepartureTime(LocalTime.parse(departureTime));
			}

			stations.add(station);
		}

		if (stations.isEmpty()) {
			throw new VoyageNotFoundException();
		}

		return new Voyage(stations, uri, ZONE_ID);
	}

}
