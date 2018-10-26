package io.traintracker.application.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import io.traintracker.application.VoyageFetcher;
import io.traintracker.application.VoyageFetcherResolver;
import org.springframework.stereotype.Component;

@Component
public class DefaultVoyageFetcherResolver implements VoyageFetcherResolver {

	private final Map<String, VoyageFetcher> fetchers;

	public DefaultVoyageFetcherResolver(List<VoyageFetcher> fetchers) {
		Map<String, VoyageFetcher> map = new HashMap<>();
		for (VoyageFetcher fetcher : fetchers) {
			map.put(fetcher.getCountry(), fetcher);
		}
		this.fetchers = Collections.unmodifiableMap(new TreeMap<>(map));
	}

	@Override
	public Set<String> supportedCountries() {
		return this.fetchers.keySet();
	}

	@Override
	public VoyageFetcher getVoyageFetcher(String country) {
		return this.fetchers.get(country);
	}

}
