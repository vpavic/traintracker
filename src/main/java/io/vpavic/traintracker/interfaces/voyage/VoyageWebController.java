package io.vpavic.traintracker.interfaces.voyage;

import java.util.Objects;
import java.util.Optional;

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

import io.vpavic.traintracker.domain.model.carrier.CarrierId;
import io.vpavic.traintracker.domain.model.carrier.Carriers;
import io.vpavic.traintracker.domain.model.voyage.Station;
import io.vpavic.traintracker.domain.model.voyage.Voyage;
import io.vpavic.traintracker.domain.model.voyage.VoyageId;
import io.vpavic.traintracker.domain.model.voyage.VoyageRepository;

@Controller
@RequestMapping(path = "/{carrierId:[a-z]+}", produces = MediaType.TEXT_HTML_VALUE)
class VoyageWebController {

	private final VoyageRepository voyageRepository;

	VoyageWebController(VoyageRepository voyageRepository) {
		Objects.requireNonNull(voyageRepository, "voyageRepository must not be null");
		this.voyageRepository = voyageRepository;
	}

	@GetMapping(path = "/voyages", headers = "HX-Request=true")
	String getVoyageFragment(@PathVariable CarrierId carrierId, @RequestParam("voyage-id") VoyageId voyageId,
			Model model, HttpServletResponse response) {
		response.setHeader("HX-Push", "/" + carrierId + "/" + voyageId);
		return getVoyage(carrierId, voyageId, model, true);
	}

	@GetMapping(path = "/{voyageId}")
	String getVoyagePage(@PathVariable CarrierId carrierId, @PathVariable VoyageId voyageId, Model model) {
		return getVoyage(carrierId, voyageId, model, false);
	}

	private String getVoyage(CarrierId carrierId, VoyageId voyageId, Model model, boolean fragment) {
		model.addAttribute("carrier", Carriers.getById(carrierId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
		model.addAttribute("voyageId", voyageId);
		Optional<Voyage> voyage = this.voyageRepository.findByCarrierIdAndVoyageId(carrierId, voyageId);
		if (voyage.isEmpty()) {
			return "not-found" + (fragment ? " :: fragment" : "");
		}
		model.addAttribute("voyage", voyage.get());
		model.addAttribute("delayLevel", calculateDelayLevel(voyage.get().getCurrentStation()));
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
