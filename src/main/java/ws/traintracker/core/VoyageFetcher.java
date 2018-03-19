package ws.traintracker.core;

public interface VoyageFetcher {

	String getCountry();

	Voyage getVoyage(String train);

}
