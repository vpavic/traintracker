package io.traintracker.app.application;

import io.traintracker.app.domain.Voyage;

public interface VoyageService {

	Voyage getVoyage(String country, String train) throws Exception;

}
