package io.github.vpavic.traintracker.application;

import io.github.vpavic.traintracker.domain.model.carrier.Carrier;
import io.github.vpavic.traintracker.domain.model.voyage.Voyage;

public interface VoyageFetcher {

    Carrier getCarrier();

    Voyage getVoyage(String train);

}
