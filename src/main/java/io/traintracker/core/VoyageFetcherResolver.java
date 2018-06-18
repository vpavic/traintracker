package io.traintracker.core;

import java.util.Set;

public interface VoyageFetcherResolver {

	Set<String> supportedCountries();

	VoyageFetcher getVoyageFetcher(String country);

}
