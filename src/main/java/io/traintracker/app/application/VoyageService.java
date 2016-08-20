package io.traintracker.app.application;

import java.util.List;

import io.traintracker.app.domain.Station;

public interface VoyageService {

	List<Station> getStations(String country, String train) throws Exception;

}
