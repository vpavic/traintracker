package net.vpavic.traintracker.domain.model.voyage;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Voyage {

	private final VoyageId id;

	private final List<Station> stations;

	private final OffsetDateTime generatedTime;

	public Voyage(VoyageId id, List<Station> stations, OffsetDateTime generatedTime) {
		Objects.requireNonNull(id, "id must not be null");
		Objects.requireNonNull(stations, "stations must not be null");
		Objects.requireNonNull(generatedTime, "generatedTime must not be null");
		this.id = id;
		this.stations = Collections.unmodifiableList(stations);
		this.generatedTime = generatedTime;
	}

	public VoyageId getId() {
		return this.id;
	}

	public Collection<Station> getStations() {
		return this.stations;
	}

	public OffsetDateTime getGeneratedTime() {
		return this.generatedTime;
	}

	public Station getCurrentStation() {
		return this.stations.get(this.stations.size() - 1);
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
