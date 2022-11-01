package net.vpavic.traintracker.infrastructure.fetcher.hzpp;

import java.time.LocalTime;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import net.vpavic.traintracker.domain.model.voyage.Station;
import net.vpavic.traintracker.domain.model.voyage.Voyage;
import net.vpavic.traintracker.domain.model.voyage.VoyageId;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDSoftAssertions.thenSoftly;

/**
 * Tests for {@link HzppHtmlParser}.
 */
@DisplayName("HZPP HTML parser")
class HzppHtmlParserTests {

	@Nested
	@DisplayName("when parse voyage page")
	class WhenParseVoyagePage {

		@Test
		@DisplayName("given voyage in progress page then returns Voyage")
		void givenVoyageInProgressPageThenReturnsVoyage() {
			// when
			Optional<Voyage> result = HzppHtmlParser.parseVoyage(HzppSampleResponses.currentPositionVoyageInProgress);
			// then
			then(result).as("Voyage").hasValueSatisfying(voyage -> thenSoftly(softly -> {
				softly.then(voyage.getId()).isEqualTo(VoyageId.of("544"));
				softly.then(voyage.getStations()).hasSize(1);
				Station currentStation = voyage.getCurrentStation();
				softly.then(currentStation.getName()).isEqualTo("LIPOVLJANI");
				softly.then(currentStation.getArrivalTime()).isNull();
				softly.then(currentStation.getArrivalDelay()).isNull();
				softly.then(currentStation.getDepartureTime()).isEqualTo(LocalTime.of(14, 3));
				softly.then(currentStation.getDepartureDelay()).isEqualTo(24);
			}));
		}

		@Test
		@DisplayName("given voyage ended page then returns Voyage")
		void givenVoyageEndedPageThenReturnsVoyage() {
			// when
			Optional<Voyage> result = HzppHtmlParser.parseVoyage(HzppSampleResponses.currentPositionVoyageEnded);
			// then
			then(result).as("Voyage").hasValueSatisfying(voyage -> thenSoftly(softly -> {
				softly.then(voyage.getId()).isEqualTo(VoyageId.of("540"));
				softly.then(voyage.getStations()).hasSize(1);
				Station currentStation = voyage.getCurrentStation();
				softly.then(currentStation.getName()).isEqualTo("ZAGREB GL. KOL.");
				softly.then(currentStation.getArrivalTime()).isEqualTo(LocalTime.of(6, 54));
				softly.then(currentStation.getArrivalDelay()).isEqualTo(10);
				softly.then(currentStation.getDepartureTime()).isNull();
				softly.then(currentStation.getDepartureDelay()).isNull();
			}));
		}

		@Test
		@DisplayName("given voyage created page then returns Voyage")
		void givenVoyageCreatedPageThenReturnsVoyage() {
			// when
			Optional<Voyage> result = HzppHtmlParser.parseVoyage(HzppSampleResponses.currentPositionVoyageCreated);
			// then
			then(result).as("Voyage").hasValueSatisfying(voyage -> thenSoftly(softly -> {
				softly.then(voyage.getId()).isEqualTo(VoyageId.of("545"));
				softly.then(voyage.getStations()).hasSize(1);
				Station currentStation = voyage.getCurrentStation();
				softly.then(currentStation.getName()).isEqualTo("ZAGREB GL. KOL.");
				softly.then(currentStation.getArrivalTime()).isEqualTo(LocalTime.of(11, 36));
				softly.then(currentStation.getArrivalDelay()).isEqualTo(0);
				softly.then(currentStation.getDepartureTime()).isNull();
				softly.then(currentStation.getDepartureDelay()).isNull();
			}));
		}

		@Test
		@DisplayName("given not found page then returns empty")
		void givenNotFoundPageThenReturnsEmpty() {
			// when
			Optional<Voyage> result = HzppHtmlParser.parseVoyage(HzppSampleResponses.currentPositionNotFound);
			// then
			then(result).as("Voyage").isEmpty();
		}

	}

}
