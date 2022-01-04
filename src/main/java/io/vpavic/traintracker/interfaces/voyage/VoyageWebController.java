package io.vpavic.traintracker.interfaces.voyage;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import io.vpavic.traintracker.application.VoyageFetcher;
import io.vpavic.traintracker.domain.model.voyage.Station;
import io.vpavic.traintracker.domain.model.voyage.Voyage;

@Controller
@RequestMapping(path = "/{carrierId:[a-z]+}", produces = MediaType.TEXT_HTML_VALUE)
class VoyageWebController {

	@GetMapping(path = "/voyages", headers = "HX-Request=true")
	String getVoyageFragment(@PathVariable("carrierId") VoyageFetcher fetcher, @RequestParam("train-no") String train,
			Model model, HttpServletResponse response) {
		response.setHeader("HX-Push", "/" + fetcher.getCarrier().getId() + "/" + train);
		return getVoyage(fetcher, train, model, true);
	}

	@GetMapping(path = "/{train}")
	String getVoyagePage(@PathVariable("carrierId") VoyageFetcher fetcher, @PathVariable String train, Model model) {
		return getVoyage(fetcher, train, model, false);
	}

	private static String getVoyage(VoyageFetcher fetcher, String train, Model model, boolean fragment) {
		if (fetcher == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		model.addAttribute("carrier", fetcher.getCarrier());
		model.addAttribute("train", train);
		Voyage voyage = fetcher.getVoyage(train);
		if (voyage == null) {
			return "not-found" + (fragment ? " :: fragment" : "");
		}
		model.addAttribute("delayLevel", calculateDelayLevel(voyage.getCurrentStation()));
		model.addAttribute("voyage", voyage);
		return "voyage" + (fragment ? " :: fragment" : "");
	}

	private static String calculateDelayLevel(Station station) {
		Integer delay = (station.getDepartureDelay() != null) ? station.getDepartureDelay() : station.getArrivalDelay();
		if (delay == null) {
			return "info";
		}
		else if (delay < 5) {
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
