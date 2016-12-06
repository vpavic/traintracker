package io.traintracker.core;

public interface VoyageService {

	Voyage getVoyage(String country, String train) throws Exception;

}
