package io.traintracker.core;

import java.time.LocalTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.Objects;

public class Voyage {

	private Collection<Station> stations;

	private Station currentStation;

	private Carrier carrier;

	private String source;

	private LocalTime generatedTime;

	public Voyage(Deque<Station> stations, Carrier carrier, String source) {
		Objects.requireNonNull(stations, "Stations must not be null");
		if (stations.isEmpty()) {
			throw new IllegalArgumentException("Stations must not be empty");
		}
		this.stations = Collections.unmodifiableCollection(stations);
		this.currentStation = stations.getLast();
		this.carrier = Objects.requireNonNull(carrier, "Carrier must not be null");
		this.source = Objects.requireNonNull(source, "Source must not be null");
		this.generatedTime = LocalTime.now();
	}

	public Collection<Station> getStations() {
		return this.stations;
	}

	public Station getCurrentStation() {
		return this.currentStation;
	}

	public Carrier getCarrier() {
		return this.carrier;
	}

	public String getSource() {
		return this.source;
	}

	public LocalTime getGeneratedTime() {
		return this.generatedTime;
	}

}
