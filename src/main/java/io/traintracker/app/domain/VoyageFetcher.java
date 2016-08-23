package io.traintracker.app.domain;

public interface VoyageFetcher {

	Voyage getVoyage(String train) throws Exception;

}
