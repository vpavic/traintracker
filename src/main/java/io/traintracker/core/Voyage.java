package io.traintracker.core;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.google.common.collect.Iterables;

public class Voyage {

	private List<Station> stations;

	private Station currentStation;

	private Carrier carrier;

	private String source;

	private LocalTime generatedTime;

	public Voyage(Collection<Station> stations, Carrier carrier, String source) {
		Objects.requireNonNull(stations, "Stations must not be null");
		if (stations.isEmpty()) {
			throw new IllegalArgumentException("Stations must not be empty");
		}
		this.stations = Collections.unmodifiableList(new ArrayList<>(stations));
		this.currentStation = Iterables.getLast(this.stations);
		this.carrier = Objects.requireNonNull(carrier, "Carrier must not be null");
		this.source = Objects.requireNonNull(source, "Source must not be null");
		this.generatedTime = LocalTime.now();
	}

	public List<Station> getStations() {
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
