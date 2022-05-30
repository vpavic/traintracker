package io.vpavic.traintracker.domain.model.voyage;

import java.util.Optional;

import io.vpavic.traintracker.domain.model.carrier.CarrierId;

public interface VoyageRepository {

	Optional<Voyage> findByCarrierIdAndVoyageId(CarrierId carrierId, VoyageId voyageId);

}
