package io.traintracker.interfaces;

import java.util.Objects;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import io.traintracker.core.Station;
import io.traintracker.core.Voyage;
import io.traintracker.core.VoyageFetcher;
import io.traintracker.core.VoyageFetcherResolver;

@Controller
public class WebController {

	private final VoyageFetcherResolver resolver;

	public WebController(VoyageFetcherResolver resolver) {
		this.resolver = Objects.requireNonNull(resolver, "VoyageFetcherResolver must not be null");
	}

	@ModelAttribute("countries")
	public Set<String> countries() {
		return this.resolver.supportedCountries();
	}

	@GetMapping(path = "/")
	public String home(@ModelAttribute("countries") Set<String> countries) {
		return "redirect:/" + countries.iterator().next() + "/";
	}

	@GetMapping(path = "/{country:[a-z]{2}}")
	public String country(@PathVariable String country) {
		return "redirect:/" + country + "/";
	}

	@GetMapping(path = "/{country:[a-z]{2}}/")
	public String country(@PathVariable String country, Model model,
			@ModelAttribute("countries") Set<String> countries) {
		model.addAttribute("country", country);
		if (!countries.contains(country)) {
			return "not-found :: fragment";
		}
		return "home";
	}

	@GetMapping(path = "/{country:[a-z]{2}}/{train}", produces = "text/html; charset=UTF-8")
	public String voyage(@PathVariable String country, @PathVariable String train,
			@RequestHeader(name = "X-PJAX", required = false) boolean pjax, Model model) {
		model.addAttribute("country", country);
		VoyageFetcher fetcher = this.resolver.getVoyageFetcher(country);
		if (fetcher == null) {
			return "not-found" + (pjax ? " :: fragment" : "");
		}
		model.addAttribute("train", train);
		Voyage voyage = fetcher.getVoyage(train);
		if (voyage == null) {
			return "not-found" + (pjax ? " :: fragment" : "");
		}
		model.addAttribute("delayLevel", calculateDelayLevel(voyage.getCurrentStation()));
		model.addAttribute("voyage", voyage);
		return "voyage" + (pjax ? " :: fragment" : "");
	}

	private String calculateDelayLevel(Station station) {
		Integer delay = station.getDepartureDelay() != null
				? station.getDepartureDelay() : station.getArrivalDelay();
		if (delay == null) {
			return "info";
		}
		else if (delay < 1) {
			return "success";
		}
		else if (delay < 20) {
			return "warning";
		}
		else {
			return "danger";
		}
	}

}
