package io.traintracker.core;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.Objects;

public class Voyage implements Serializable {

	private Collection<Station> stations;

	private Station currentStation;

	private Carrier carrier;

	private String source;

	private LocalTime generatedTime;

	public Voyage(Station currentStation, Deque<Station> stations, Carrier carrier, String source) {
		Objects.requireNonNull(currentStation, "Current station must not be null");
		Objects.requireNonNull(stations, "Stations must not be null");
		Objects.requireNonNull(carrier, "Carrier must not be null");
		Objects.requireNonNull(source, "Source must not be null");
		this.currentStation = currentStation;
		this.stations = Collections.unmodifiableCollection(stations);
		this.carrier = carrier;
		this.source = source;
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
