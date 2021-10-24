package io.vpavic.traintracker.infrastructure.web;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import io.vpavic.traintracker.application.VoyageFetcher;
import io.vpavic.traintracker.domain.model.carrier.CarrierId;

@Component
class VoyageFetcherConverter implements Converter<String, VoyageFetcher> {

    private final Map<CarrierId, VoyageFetcher> fetchers;

    VoyageFetcherConverter(List<VoyageFetcher> fetchers) {
        this.fetchers = fetchers.stream()
                .collect(Collectors.toUnmodifiableMap(fetcher -> fetcher.getCarrier().getId(), fetcher -> fetcher));
    }

    @Override
    public VoyageFetcher convert(@NonNull String source) {
        VoyageFetcher fetcher = this.fetchers.get(CarrierId.of(source));
        if (fetcher == null) {
            throw new IllegalArgumentException("Unknown carrier: " + source);
        }
        return fetcher;
    }

}
