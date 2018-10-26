package io.traintracker.application;

import java.util.Set;

public interface VoyageFetcherResolver {

	Set<String> supportedCountries();

	VoyageFetcher getVoyageFetcher(String country);

}
