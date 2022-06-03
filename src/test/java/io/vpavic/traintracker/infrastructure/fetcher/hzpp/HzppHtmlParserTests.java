package io.vpavic.traintracker.infrastructure.fetcher.hzpp;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import io.vpavic.traintracker.domain.model.voyage.Station;
import io.vpavic.traintracker.domain.model.voyage.Voyage;
import io.vpavic.traintracker.domain.model.voyage.VoyageId;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDSoftAssertions.thenSoftly;

class HzppHtmlParserTests {

	@Test
	void parseVoyage_VoyageInProgress_ShouldReturnVoyage() {
		// when
		Voyage voyage = HzppHtmlParser.parseVoyage(HzppSampleResponses.currentPositionVoyageInProgress);
		// then
		then(voyage).as("Voyage").isNotNull();
		thenSoftly(softly -> {
			softly.then(voyage.getId()).isEqualTo(VoyageId.of("544"));
			softly.then(voyage.getStations()).hasSize(1);
			Station currentStation = voyage.getCurrentStation();
			softly.then(currentStation.getName()).isEqualTo("LIPOVLJANI");
			softly.then(currentStation.getArrivalTime()).isNull();
			softly.then(currentStation.getArrivalDelay()).isNull();
			softly.then(currentStation.getDepartureTime()).isEqualTo(LocalTime.of(14, 3));
			softly.then(currentStation.getDepartureDelay()).isEqualTo(24);
		});
	}

	@Test
	void parseVoyage_VoyageEnded_ShouldReturnVoyage() {
		// when
		Voyage voyage = HzppHtmlParser.parseVoyage(HzppSampleResponses.currentPositionVoyageEnded);
		// then
		then(voyage).as("Voyage").isNotNull();
		thenSoftly(softly -> {
			softly.then(voyage.getId()).isEqualTo(VoyageId.of("540"));
			softly.then(voyage.getStations()).hasSize(1);
			Station currentStation = voyage.getCurrentStation();
			softly.then(currentStation.getName()).isEqualTo("ZAGREB GL. KOL.");
			softly.then(currentStation.getArrivalTime()).isEqualTo(LocalTime.of(6, 54));
			softly.then(currentStation.getArrivalDelay()).isEqualTo(10);
			softly.then(currentStation.getDepartureTime()).isNull();
			softly.then(currentStation.getDepartureDelay()).isNull();
		});
	}

	@Test
	void parseVoyage_NotFoundResponse_ShouldReturnNull() {
		// when
		Voyage voyage = HzppHtmlParser.parseVoyage(HzppSampleResponses.currentPositionNotFound);
		// then
		then(voyage).as("Voyage").isNull();
	}

}
