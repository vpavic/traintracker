package io.traintracker.interfaces;

import java.util.Objects;

import io.traintracker.core.Voyage;
import io.traintracker.core.VoyageFetcherResolver;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/{country:[a-z]{2}}/{train}")
public class ApiController {

	private final VoyageFetcherResolver resolver;

	public ApiController(VoyageFetcherResolver resolver) {
		this.resolver = Objects.requireNonNull(resolver, "VoyageFetcherResolver must not be null");
	}

	@GetMapping
	public Voyage voyage(@PathVariable String country, @PathVariable String train) {
		return this.resolver.getVoyageFetcher(country)
				.orElseThrow(UnsupportedCountryException::new)
				.getVoyage(train)
				.orElseThrow(VoyageNotFoundException::new);
	}

}
