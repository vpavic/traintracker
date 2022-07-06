package xyz.vpavic.traintracker.infrastructure.json.jackson;

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.List;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import xyz.vpavic.traintracker.domain.model.voyage.Station;
import xyz.vpavic.traintracker.domain.model.voyage.Voyage;
import xyz.vpavic.traintracker.domain.model.voyage.VoyageId;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDSoftAssertions.thenSoftly;

/**
 * Tests for {@link TrainTrackerModule}.
 */
@DisplayName("TrainTracker Jackson module")
class TrainTrackerModuleTests {

	private static final String STATION_JSON = """
			{
				"name":"Test",
				"arrivalTime":"12:10:00",
				"arrivalDelay":10,
				"departureTime":"12:20:00",
				"departureDelay":15
			}
			""";

	private static final String VOYAGE_JSON = """
			{
				"id":"123",
				"stations":[],
				"generatedTime":"1970-01-01T00:00:00Z"
			}
			""";

	private static final JsonMapper jsonMapper = JsonMapper.builder()
			.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
			.disable(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS)
			.addModule(new JavaTimeModule())
			.addModule(new TrainTrackerModule())
			.build();

	@Nested
	@DisplayName("when write value")
	class WhenWriteValue {

		@Test
		@DisplayName("given Station then writes JSON")
		void givenStationThenWritesJson() throws Exception {
			// given
			Station station = new Station("Test");
			station.setArrivalTime(LocalTime.of(12, 10));
			station.setArrivalDelay(10);
			station.setDepartureTime(LocalTime.of(12, 20));
			station.setDepartureDelay(15);
			// when
			String json = jsonMapper.writeValueAsString(station);
			// then
			then(json).as("Station JSON").isEqualToIgnoringWhitespace(STATION_JSON);
		}

		@Test
		@DisplayName("given Voyage then writes JSON")
		void givenVoyageThenWritesJson() throws Exception {
			// given
			Voyage voyage = new Voyage(VoyageId.of("123"), List.of(), Instant.EPOCH.atOffset(ZoneOffset.UTC));
			// when
			String json = jsonMapper.writeValueAsString(voyage);
			// then
			then(json).as("Voyage JSON").isEqualToIgnoringWhitespace(VOYAGE_JSON);
		}
	}

	@Nested
	@DisplayName("when read value")
	class WhenReadValue {

		@Test
		@DisplayName("given valid JSON then reads Station")
		void givenValidJsonThenReadsStation() throws Exception {
			// when
			Station station = jsonMapper.readValue(STATION_JSON, Station.class);
			// then
			thenSoftly(softly -> {
				softly.then(station.getName()).as("Station name").isEqualTo("Test");
				softly.then(station.getArrivalTime()).as("Arrival time").isEqualTo(LocalTime.of(12, 10));
				softly.then(station.getArrivalDelay()).as("Arrival delay").isEqualTo(10);
				softly.then(station.getDepartureTime()).as("Departure time").isEqualTo(LocalTime.of(12, 20));
				softly.then(station.getDepartureDelay()).as("Departure delay").isEqualTo(15);
			});
		}

		@Test
		@DisplayName("given valid JSON then reads Voyage")
		void givenValidJsonThenReadsVoyage() throws Exception {
			// when
			Voyage voyage = jsonMapper.readValue(VOYAGE_JSON, Voyage.class);
			// then
			thenSoftly(softly -> {
				softly.then(voyage.getId()).as("Voyage id").isEqualTo(VoyageId.of("123"));
				softly.then(voyage.getStations()).as("Stations").isEmpty();
				softly.then(voyage.getGeneratedTime()).as("Generated time")
						.isEqualTo(Instant.EPOCH.atOffset(ZoneOffset.UTC));
			});
		}
	}

}
