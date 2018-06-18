package io.traintracker.core;

import java.time.LocalTime;

public class Station {

	private String name;

	private LocalTime arrivalTime;

	private Integer arrivalDelay;

	private LocalTime departureTime;

	private Integer departureDelay;

	public Station(String name) {
		this.name = name;
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
