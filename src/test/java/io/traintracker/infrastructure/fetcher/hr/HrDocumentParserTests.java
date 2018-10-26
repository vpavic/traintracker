/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.traintracker.infrastructure.fetcher.hr;

import java.io.File;
import java.net.URI;
import java.time.LocalTime;
import java.util.Deque;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import io.traintracker.domain.model.voyage.Station;

import static org.assertj.core.api.Assertions.assertThat;

public class HrDocumentParserTests {

	@Test
	public void testPkvlOk() {
		Document doc = getDocument("/hr-pkvl-ok.html");
		Deque<Station> stations = HrDocumentParser.parseOverview(doc);
		assertThat(stations).hasSize(35);
		Station currentStation = stations.getLast();
		assertThat(currentStation.getName()).isEqualTo("VINKOVCI");
		assertThat(currentStation.getArrivalTime()).isEqualTo(LocalTime.of(21, 28));
		assertThat(currentStation.getArrivalDelay()).isEqualTo(21);
		assertThat(currentStation.getDepartureTime()).isNull();
		assertThat(currentStation.getDepartureDelay()).isNull();
	}

	@Test
	public void testPkvlNotFound() {
		Document doc = getDocument("/hr-pkvl-not-found.html");
		Deque<Station> stations = HrDocumentParser.parseOverview(doc);
		assertThat(stations).isEmpty();
	}

	@Test
	public void testTpvlOk() {
		Document doc = getDocument("/hr-tpvl-ok.html");
		Station station = HrDocumentParser.parseCurrentPosition(doc);
		assertThat(station).isNotNull();
		assertThat(station.getName()).isEqualTo("NOVA KAPELA BATRINA");
		assertThat(station.getArrivalTime()).isNull();
		assertThat(station.getArrivalDelay()).isNull();
		assertThat(station.getDepartureTime()).isEqualTo(LocalTime.of(5, 43));
		assertThat(station.getDepartureDelay()).isEqualTo(27);
	}

	@Test
	public void testTpvlNotFound() {
		Document doc = getDocument("/hr-tpvl-not-found.html");
		Station station = HrDocumentParser.parseCurrentPosition(doc);
		assertThat(station).isNull();
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
