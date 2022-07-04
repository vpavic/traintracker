package xyz.vpavic.traintracker.infrastructure.fetcher;

import java.util.Optional;

import xyz.vpavic.traintracker.domain.model.carrier.Carrier;
import xyz.vpavic.traintracker.domain.model.voyage.Voyage;
import xyz.vpavic.traintracker.domain.model.voyage.VoyageId;

public interface VoyageFetcher {

	Carrier getCarrier();

	Optional<Voyage> getVoyage(VoyageId voyageId);

}
