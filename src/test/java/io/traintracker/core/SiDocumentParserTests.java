package io.traintracker.core;

import java.io.File;
import java.net.URI;
import java.time.LocalTime;
import java.util.Collection;

import com.google.common.collect.Iterables;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SiDocumentParserTests {

	@Test
	public void testOk() {
		Document doc = getDocument("/si-ok.html");
		Collection<Station> stations = SiDocumentParser.parse(doc);
		assertThat(stations).hasSize(12);
		Station currentStation = Iterables.getLast(stations);
		assertThat(currentStation.getName()).isEqualTo("Zaprešič (HR)");
		assertThat(currentStation.getArrivalTime()).isEqualTo(LocalTime.of(16, 53));
		assertThat(currentStation.getArrivalDelay()).isNull();
		assertThat(currentStation.getDepartureTime()).isNull();
		assertThat(currentStation.getDepartureDelay()).isNull();
	}

	@Test
	public void testNotFound() {
		Document doc = getDocument("/si-not-found.html");
		Collection<Station> stations = SiDocumentParser.parse(doc);
		assertThat(stations).isEmpty();
	}

	private Document getDocument(String path) {
		try {
			URI uri = getClass().getResource(path).toURI();
			return Jsoup.parse(new File(uri), "UTF-8");
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
