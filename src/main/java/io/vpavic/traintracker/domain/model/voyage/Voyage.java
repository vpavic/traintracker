package io.vpavic.traintracker.domain.model.voyage;

import java.io.Serializable;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Voyage implements Serializable {

	private final VoyageId id;

	private final LocalDate date;

	private final Station currentStation;

	private final Collection<Station> stations;

	private final List<URI> sources;

	private final LocalTime generatedTime;

	public Voyage(VoyageId id, LocalDate date, Station currentStation, Collection<Station> stations,
			List<URI> sources, LocalTime generatedTime) {
		Objects.requireNonNull(id, "id must not be null");
		Objects.requireNonNull(currentStation, "currentStation must not be null");
		Objects.requireNonNull(stations, "stations must not be null");
		this.id = id;
		this.date = date;
		this.currentStation = currentStation;
		this.stations = Collections.unmodifiableCollection(stations);
		this.sources = Collections.unmodifiableList(sources);
		this.generatedTime = generatedTime;
	}

	public VoyageId getId() {
		return this.id;
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

	@Override
	public int hashCode() {
		return Objects.hash(this.id);
	}

	@Override
	public String toString() {
		return this.id.toString();
	}

}
