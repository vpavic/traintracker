package io.traintracker.core;

import java.util.Optional;
import java.util.Set;

public interface VoyageFetcherResolver {

	Set<String> supportedCountries();

	Optional<VoyageFetcher> getVoyageFetcher(String country);

}
