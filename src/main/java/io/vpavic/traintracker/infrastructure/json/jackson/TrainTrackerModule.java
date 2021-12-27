package io.vpavic.traintracker.infrastructure.json.jackson;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import io.vpavic.traintracker.domain.model.carrier.CarrierId;
import io.vpavic.traintracker.domain.model.voyage.Station;
import io.vpavic.traintracker.domain.model.voyage.Voyage;

public class TrainTrackerModule extends SimpleModule {

    public TrainTrackerModule() {
        super("TrainTracker");
        addSerializer(CarrierId.class, new ToStringSerializer());
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
        VoyageMixin(@JsonProperty("carrierId") CarrierId carrierId, @JsonProperty("date") LocalDate date,
                @JsonProperty("currentStation") Station currentStation,
                @JsonProperty("stations") Collection<Station> stations, @JsonProperty("sources") List<URI> sources,
                @JsonProperty("generatedTime") LocalTime generatedTime) {
        }

    }

}
