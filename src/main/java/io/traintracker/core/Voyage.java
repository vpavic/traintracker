package io.traintracker.core;

import java.net.URI;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.google.common.collect.Iterables;

public class Voyage {

	private List<Station> stations;

	private Station currentStation;

	private URI source;

	private ZoneId timezone;

	private LocalTime generatedTime;

	public Voyage(Collection<Station> stations, URI source, ZoneId timezone) {
		if (Objects.requireNonNull(stations).isEmpty()) {
			throw new IllegalArgumentException("Stations must not be empty");
		}
		this.stations = Collections.unmodifiableList(new ArrayList<>(stations));
		this.currentStation = Iterables.getLast(this.stations);
		this.source = Objects.requireNonNull(source);
		this.timezone = Objects.requireNonNull(timezone);
		this.generatedTime = LocalTime.now();
	}

	public List<Station> getStations() {
		return this.stations;
	}

	public Station getCurrentStation() {
		return this.currentStation;
	}

	public URI getSource() {
		return this.source;
	}

	public ZoneId getTimezone() {
		return this.timezone;
	}

	public LocalTime getGeneratedTime() {
		return this.generatedTime;
	}

}
