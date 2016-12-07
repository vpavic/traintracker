package io.traintracker.interfaces;

import java.util.Set;

import io.traintracker.core.Station;
import io.traintracker.core.UnsupportedCountryException;
import io.traintracker.core.Voyage;
import io.traintracker.core.VoyageNotFoundException;
import io.traintracker.core.VoyageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static java.util.Objects.requireNonNull;

@Controller
@RequestMapping(path = "/{country:[a-z]{2}}")
public class WebController {

	private final VoyageService voyageService;

	public WebController(VoyageService voyageService) {
		this.voyageService = requireNonNull(voyageService);
	}

	@GetMapping
	public String home(@PathVariable String country, Model model) {
		Set<String> supportedCountries = this.voyageService.supportedCountries();
		model.addAttribute("country", country);
		model.addAttribute("supportedCountries", supportedCountries);
		if (!supportedCountries.contains(country)) {
			return "not-found :: fragment";
		}
		return "home";
	}

	@GetMapping(path = "/{train}")
	public String voyage(@PathVariable String country, @PathVariable String train, Model model) {
		model.addAttribute("country", country);
		model.addAttribute("train", train);
		try {
			Voyage voyage = this.voyageService.getVoyage(country, train);
			model.addAttribute("delay", calculateDelay(voyage.getCurrentStation()));
			model.addAttribute("voyage", voyage);
			return "voyage :: fragment";
		}
		catch (UnsupportedCountryException | VoyageNotFoundException e) {
			return "not-found :: fragment";
		}
	}

	private Integer calculateDelay(Station station) {
		if (station.getDepartureDelay() != null) {
			return station.getDepartureDelay();
		}
		return station.getArrivalDelay();
	}

}
