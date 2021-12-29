package io.vpavic.traintracker.application;

import io.vpavic.traintracker.domain.model.carrier.Carrier;
import io.vpavic.traintracker.domain.model.voyage.Voyage;

public interface VoyageFetcher {

	Carrier getCarrier();

	Voyage getVoyage(String train);

}
