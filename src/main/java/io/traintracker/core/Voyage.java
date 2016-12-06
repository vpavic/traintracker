package io.traintracker.core;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.Iterables;

public class Voyage {

	private Station currentStation;
	private List<Station> stations;
	private LocalTime generatedTime;

	public Voyage(Collection<Station> stations) {
		if (stations == null || stations.isEmpty()) {
			throw new IllegalArgumentException("Stations must not be empty");
		}
		this.currentStation = Iterables.getLast(stations);
		this.stations = Collections.unmodifiableList(new ArrayList<>(stations));
		this.generatedTime = LocalTime.now();
	}

	public Station getCurrentStation() {
		return this.currentStation;
	}

	public List<Station> getStations() {
		return this.stations;
	}

	public LocalTime getGeneratedTime() {
		return this.generatedTime;
	}

}
