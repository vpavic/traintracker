package io.vpavic.traintracker.infrastructure.json.jackson;

import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import io.vpavic.traintracker.domain.model.voyage.Station;
import io.vpavic.traintracker.domain.model.voyage.Voyage;
import io.vpavic.traintracker.domain.model.voyage.VoyageId;

public class TrainTrackerModule extends SimpleModule {

	public TrainTrackerModule() {
		super("TrainTracker");
		addSerializer(VoyageId.class, new ToStringSerializer());
		setMixInAnnotation(Station.class, StationMixin.class);
		setMixInAnnotation(Voyage.class, VoyageMixin.class);
	}

	static abstract class StationMixin {

		@SuppressWarnings("unused")
		StationMixin(@JsonProperty("name") String name, @JsonProperty("arrivalTime") LocalTime arrivalTime,
				@JsonProperty("arrivalDelay") Integer arrivalDelay,
				@JsonProperty("departureTime") LocalTime departureTime,
				@JsonProperty("departureDelay") Integer departureDelay) {
		}

	}

	static abstract class VoyageMixin {

		@SuppressWarnings("unused")
		VoyageMixin(@JsonProperty("id") VoyageId id, @JsonProperty("stations") List<Station> stations,
				@JsonProperty("generatedTime") OffsetDateTime generatedTime) {
		}

		@JsonIgnore
		@SuppressWarnings("unused")
		abstract Station getCurrentStation();

	}

}
