package io.traintracker.core;

import java.util.Optional;

public interface VoyageFetcher {

	String getCountry();

	Optional<Voyage> getVoyage(String train);

}
