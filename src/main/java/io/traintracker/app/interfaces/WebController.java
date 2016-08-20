package io.traintracker.app.interfaces;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.google.common.collect.Iterables;
import io.traintracker.app.domain.Station;
import io.traintracker.app.application.VoyageService;
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
		List<Station> stations = voyageService.getStations(country, train);
		model.addAttribute("train", train);
		model.addAttribute("generatedTime", LocalTime.now().truncatedTo(ChronoUnit.SECONDS));
		if (!stations.isEmpty()) {
			Station currentStation = Iterables.getLast(stations);
			model.addAttribute("currentStation", currentStation);
			model.addAttribute("delay", calculateDelay(currentStation));
			model.addAttribute("stations", stations);
			return "voyage :: fragment";
		}
		else {
			return "not-found :: fragment";
		}
	}

	private int calculateDelay(Station station) {
		if (station.getDepartureTime() != null && station.getDepartureDelay() != null) {
			return station.getDepartureDelay();
		}
		else if (station.getArrivalDelay() != null) {
			return station.getArrivalDelay();
		}
		else {
			return 0;
		}
	}

}
