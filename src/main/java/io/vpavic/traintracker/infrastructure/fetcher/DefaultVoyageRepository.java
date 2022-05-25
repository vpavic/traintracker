package io.vpavic.traintracker.infrastructure.fetcher;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import io.vpavic.traintracker.domain.model.carrier.CarrierId;
import io.vpavic.traintracker.domain.model.voyage.Voyage;
import io.vpavic.traintracker.domain.model.voyage.VoyageRepository;

@Component
class DefaultVoyageRepository implements VoyageRepository {

	private final Map<CarrierId, VoyageFetcher> voyageFetchers;

	DefaultVoyageRepository(List<VoyageFetcher> voyageFetchers) {
		this.voyageFetchers = voyageFetchers.stream()
				.collect(Collectors.toUnmodifiableMap(fetcher -> fetcher.getCarrier().getId(), fetcher -> fetcher));
	}

	@Override
	public Optional<Voyage> findByCarrierIdAndVoyageId(CarrierId carrierId, String voyageId) {
		Objects.requireNonNull(carrierId, "carrierId must not be null");
		Objects.requireNonNull(voyageId, "voyageId must not be null");
		VoyageFetcher voyageFetcher = this.voyageFetchers.get(carrierId);
		if (voyageFetcher == null) {
			return Optional.empty();
		}
		return voyageFetcher.getVoyage(voyageId);
	}

}
