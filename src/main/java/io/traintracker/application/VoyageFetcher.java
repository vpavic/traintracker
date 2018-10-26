package io.traintracker.application;

import io.traintracker.domain.model.voyage.Voyage;

public interface VoyageFetcher {

	String getCountry();

	Voyage getVoyage(String train);

}
