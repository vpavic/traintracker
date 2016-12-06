package io.traintracker.core;

public interface VoyageFetcher {

	Voyage getVoyage(String train) throws VoyageNotFoundException;

}
