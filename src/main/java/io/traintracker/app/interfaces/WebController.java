package io.traintracker.app.interfaces;

import io.traintracker.app.application.VoyageService;
import io.traintracker.app.domain.Station;
import io.traintracker.app.domain.Voyage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static java.util.Objects.requireNonNull;

@Controller
@RequestMapping(path = "/{country}/{train}")
public class WebController {

	private final VoyageService voyageService;

	public WebController(VoyageService voyageService) {
		this.voyageService = requireNonNull(voyageService);
	}

	@GetMapping
	public String voyage(@PathVariable String country, @PathVariable String train, Model model)
			throws Exception {
		Voyage voyage = this.voyageService.getVoyage(country, train);
		model.addAttribute("train", train);
		if (voyage == null) {
			return "not-found :: fragment";
		}
		model.addAttribute("delay", calculateDelay(voyage.getCurrentStation()));
		model.addAttribute("voyage", voyage);
		return "voyage :: fragment";
	}

	private Integer calculateDelay(Station station) {
		if (station.getDepartureDelay() != null) {
			return station.getDepartureDelay();
		}
		return station.getArrivalDelay();
	}

}
