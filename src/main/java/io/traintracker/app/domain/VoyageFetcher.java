package io.traintracker.app.domain;

import java.util.List;

import io.traintracker.app.domain.Station;

public interface VoyageFetcher {

	List<Station> getStations(String train) throws Exception;

}
