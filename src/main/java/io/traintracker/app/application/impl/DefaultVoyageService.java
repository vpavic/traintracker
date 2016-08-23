package io.traintracker.app.application.impl;

import java.util.HashMap;
import java.util.Map;

import io.traintracker.app.application.VoyageService;
import io.traintracker.app.domain.Voyage;
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
	public Voyage getVoyage(String country, String train) throws Exception {
		VoyageFetcher fetcher = this.fetchers.get(country);
		if (fetcher == null) {
			return null;
		}
		return fetcher.getVoyage(train);
	}

	private String getKey(String name) {
		int index = name.indexOf("VoyageFetcher");
		if (index > 0) {
			return name.substring(0, index);
		}
		return name.toLowerCase();
	}

}
