package net.vpavic.traintracker.infrastructure.fetcher;

import java.util.Optional;

import net.vpavic.traintracker.domain.model.agency.Agency;
import net.vpavic.traintracker.domain.model.voyage.Voyage;
import net.vpavic.traintracker.domain.model.voyage.VoyageId;

public interface VoyageFetcher {

	Agency getAgency();

	Optional<Voyage> getVoyage(VoyageId voyageId);

}
