package io.vpavic.traintracker.domain.model.voyage;

import java.io.Serializable;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import io.vpavic.traintracker.domain.model.carrier.Carrier;

public class Voyage implements Serializable {

    private final Carrier carrier;

    private final LocalDate date;

    private final Station currentStation;

    private final Collection<Station> stations;

    private final List<URI> sources;

    private final LocalTime generatedTime;

    public Voyage(Carrier carrier, LocalDate date, Station currentStation, Collection<Station> stations,
            List<URI> sources, LocalTime generatedTime) {
        Objects.requireNonNull(carrier, "Carrier must not be null");
        Objects.requireNonNull(currentStation, "Current station must not be null");
        Objects.requireNonNull(stations, "Stations must not be null");
        this.carrier = carrier;
        this.date = date;
        this.currentStation = currentStation;
        this.stations = Collections.unmodifiableCollection(stations);
        this.sources = Collections.unmodifiableList(sources);
        this.generatedTime = generatedTime;
    }

    public Carrier getCarrier() {
        return this.carrier;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public Station getCurrentStation() {
        return this.currentStation;
    }

    public Collection<Station> getStations() {
        return this.stations;
    }

    public List<URI> getSources() {
        return this.sources;
    }

    public LocalTime getGeneratedTime() {
        return this.generatedTime;
    }

}
