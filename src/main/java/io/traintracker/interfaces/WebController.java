package io.traintracker.interfaces;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import io.traintracker.core.Station;
import io.traintracker.core.Voyage;
import io.traintracker.core.VoyageFetcher;
import io.traintracker.core.VoyageFetcherResolver;

@Controller
@RequestMapping(path = "/{country:[a-z]{2}}")
public class WebController {

	private final VoyageFetcherResolver resolver;

	public WebController(VoyageFetcherResolver resolver) {
		this.resolver = Objects.requireNonNull(resolver, "VoyageFetcherResolver must not be null");
	}

	@ModelAttribute("supportedCountries")
	public Set<String> supportedCountries() {
		return this.resolver.supportedCountries();
	}

	@GetMapping
	public String home(@PathVariable String country, Model model) {
		model.addAttribute("country", country);
		return "home";
	}

	@GetMapping(path = "/{train}")
	public String voyage(@PathVariable String country, @PathVariable String train, Model model) {
		model.addAttribute("country", country);
		model.addAttribute("train", train);
		Optional<VoyageFetcher> fetcher = this.resolver.getVoyageFetcher(country);
		if (fetcher.isPresent()) {
			Optional<Voyage> voyage = fetcher.get().getVoyage(train);
			if (voyage.isPresent()) {
				model.addAttribute("delay", calculateDelay(voyage.get().getCurrentStation()));
				model.addAttribute("voyage", voyage.get());
				return "voyage :: fragment";
			}
		}
		return "not-found :: fragment";
	}

	private Integer calculateDelay(Station station) {
		if (station.getDepartureDelay() != null) {
			return station.getDepartureDelay();
		}
		return station.getArrivalDelay();
	}

}
