package xyz.vpavic.traintracker.domain.model.voyage;

import java.util.Optional;

import xyz.vpavic.traintracker.domain.model.agency.AgencyId;

public interface VoyageRepository {

	Optional<Voyage> findByAgencyIdAndVoyageId(AgencyId agencyId, VoyageId voyageId);

}
