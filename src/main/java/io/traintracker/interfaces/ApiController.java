package io.traintracker.interfaces;

import java.util.Objects;

import io.traintracker.core.Voyage;
import io.traintracker.core.VoyageFetcher;
import io.traintracker.core.VoyageFetcherResolver;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

	private final VoyageFetcherResolver resolver;

	public ApiController(VoyageFetcherResolver resolver) {
		this.resolver = Objects.requireNonNull(resolver, "VoyageFetcherResolver must not be null");
	}

	@GetMapping(path = "/{country:[a-z]{2}}/{train}", produces = "application/json")
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
