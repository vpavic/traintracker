package io.traintracker.interfaces.voyage;

import java.util.Objects;

import io.traintracker.domain.model.voyage.Voyage;
import io.traintracker.application.VoyageFetcher;
import io.traintracker.application.VoyageFetcherResolver;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

	private final VoyageFetcherResolver resolver;

	public ApiController(VoyageFetcherResolver resolver) {
		this.resolver = Objects.requireNonNull(resolver, "VoyageFetcherResolver must not be null");
	}

	@GetMapping(path = "/{country:[a-z]{2}}/{train}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Voyage voyage(@PathVariable String country, @PathVariable String train) {
		VoyageFetcher fetcher = this.resolver.getVoyageFetcher(country);
		if (fetcher == null) {
			throw new UnsupportedCountryException(country);
		}
		Voyage voyage = fetcher.getVoyage(train);
		if (voyage == null) {
			throw new VoyageNotFoundException(train);
		}
		return voyage;
	}

}
