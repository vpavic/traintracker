package io.traintracker.core;

import java.util.Set;

public interface VoyageService {

	Set<String> supportedCountries();

	Voyage getVoyage(String country, String train) throws UnsupportedCountryException,
			VoyageNotFoundException;

}
