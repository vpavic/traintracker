package net.vpavic.traintracker.domain.model.voyage;

import java.util.Optional;

import net.vpavic.traintracker.domain.model.agency.AgencyId;

public interface VoyageRepository {

	Optional<Voyage> findByAgencyIdAndVoyageId(AgencyId agencyId, VoyageId voyageId);

}
