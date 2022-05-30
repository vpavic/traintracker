package io.vpavic.traintracker.infrastructure.fetcher;

import java.util.Optional;

import io.vpavic.traintracker.domain.model.carrier.Carrier;
import io.vpavic.traintracker.domain.model.voyage.Voyage;
import io.vpavic.traintracker.domain.model.voyage.VoyageId;

public interface VoyageFetcher {

	Carrier getCarrier();

	Optional<Voyage> getVoyage(VoyageId voyageId);

}
