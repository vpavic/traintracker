package io.traintracker.core;

import java.io.File;
import java.net.URI;
import java.time.LocalTime;
import java.util.Deque;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HrDocumentParserTests {

	@Test
	public void testOk() {
		Document doc = getDocument("/hr-ok.html");
		Deque<Station> stations = HrDocumentParser.parse(doc);
		assertThat(stations).hasSize(35);
		Station currentStation = stations.getLast();
		assertThat(currentStation.getName()).isEqualTo("VINKOVCI");
		assertThat(currentStation.getArrivalTime()).isEqualTo(LocalTime.of(21, 28));
		assertThat(currentStation.getArrivalDelay()).isEqualTo(21);
		assertThat(currentStation.getDepartureTime()).isNull();
		assertThat(currentStation.getDepartureDelay()).isNull();
	}

	@Test
	public void testNotFound() {
		Document doc = getDocument("/hr-not-found.html");
		Deque<Station> stations = HrDocumentParser.parse(doc);
		assertThat(stations).isEmpty();
	}

	private Document getDocument(String path) {
		try {
			URI uri = getClass().getResource(path).toURI();
			return Jsoup.parse(new File(uri), "Cp1250");
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
