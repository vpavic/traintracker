package io.github.vpavic.traintracker.application;

import io.github.vpavic.traintracker.domain.model.voyage.Voyage;

public interface VoyageFetcher {

    String getCountry();

    Voyage getVoyage(String train);

}
