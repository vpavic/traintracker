package io.github.vpavic.traintracker.infrastructure.web;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import io.github.vpavic.traintracker.application.VoyageFetcher;

@Component
class VoyageFetcherConverter implements Converter<String, VoyageFetcher> {

    private final Map<String, VoyageFetcher> fetchers;

    VoyageFetcherConverter(List<VoyageFetcher> fetchers) {
        Map<String, VoyageFetcher> map = new HashMap<>();
        for (VoyageFetcher fetcher : fetchers) {
            map.put(fetcher.getCountry(), fetcher);
        }
        this.fetchers = Collections.unmodifiableMap(new TreeMap<>(map));
    }

    @Override
    public VoyageFetcher convert(@NonNull String source) {
        return this.fetchers.get(source);
    }

}
