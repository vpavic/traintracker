package io.traintracker.app.application.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.traintracker.app.domain.Station;
import io.traintracker.app.application.VoyageService;
import io.traintracker.app.domain.VoyageFetcher;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class DefaultVoyageService implements VoyageService {

	private final Map<String, VoyageFetcher> fetchers;

	public DefaultVoyageService(Map<String, VoyageFetcher> fetchers) {
		this.fetchers = new HashMap<>(fetchers.size());
		for (Map.Entry<String, VoyageFetcher> entry : fetchers.entrySet()) {
			this.fetchers.put(getKey(entry.getKey()), entry.getValue());
		}
	}

	@Override
	@Cacheable(cacheNames = "voyages")
	public List<Station> getStations(String country, String train) throws Exception {
		VoyageFetcher fetcher = this.fetchers.get(country);
		if (fetcher == null) {
			return Collections.emptyList();
		}
		return fetcher.getStations(train);
	}

	private String getKey(String name) {
		int index = name.toLowerCase().indexOf("voyagefetcher");
		if (index > 0) {
			return name.substring(0, index);
		}
		return name;
	}

}
