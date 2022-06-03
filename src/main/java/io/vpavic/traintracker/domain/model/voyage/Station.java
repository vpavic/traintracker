package io.vpavic.traintracker.domain.model.voyage;

import java.time.LocalTime;
import java.util.Objects;

public class Station {

	private final String name;

	private LocalTime arrivalTime;

	private Integer arrivalDelay;

	private LocalTime departureTime;

	private Integer departureDelay;

	private Station(String name, LocalTime arrivalTime, Integer arrivalDelay, LocalTime departureTime,
			Integer departureDelay) {
		Objects.requireNonNull(name, "name must not be null");
		this.name = name;
		this.arrivalTime = arrivalTime;
		this.arrivalDelay = arrivalDelay;
		this.departureTime = departureTime;
		this.departureDelay = departureDelay;
	}

	public Station(String name) {
		this(name, null, null, null, null);
	}

	public String getName() {
		return this.name;
	}

	public LocalTime getArrivalTime() {
		return this.arrivalTime;
	}

	public void setArrivalTime(LocalTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public Integer getArrivalDelay() {
		return this.arrivalDelay;
	}

	public void setArrivalDelay(Integer arrivalDelay) {
		this.arrivalDelay = arrivalDelay;
	}

	public LocalTime getDepartureTime() {
		return this.departureTime;
	}

	public void setDepartureTime(LocalTime departureTime) {
		this.departureTime = departureTime;
	}

	public Integer getDepartureDelay() {
		return this.departureDelay;
	}

	public void setDepartureDelay(Integer departureDelay) {
		this.departureDelay = departureDelay;
	}

}
