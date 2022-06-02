package io.vpavic.traintracker.interfaces.voyage;

import java.util.Objects;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
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

import io.vpavic.traintracker.domain.model.carrier.Carrier;
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
			HttpServletRequest request, HttpServletResponse response, Model model) {
		Carrier carrier = resolveCarrier(carrierId);
		response.setHeader("HX-Push", request.getContextPath() + "/" + carrier.getId() + "/" + voyageId);
		return getVoyage(carrier, voyageId, model, true);
	}

	@GetMapping(path = "/{voyageId}")
	String getVoyagePage(@PathVariable CarrierId carrierId, @PathVariable VoyageId voyageId, Model model) {
		Carrier carrier = resolveCarrier(carrierId);
		return getVoyage(carrier, voyageId, model, false);
	}

	private static Carrier resolveCarrier(CarrierId carrierId) {
		return Carriers.getById(carrierId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	private String getVoyage(Carrier carrier, VoyageId voyageId, Model model, boolean fragment) {
		model.addAttribute("carrier", carrier);
		model.addAttribute("voyageId", voyageId);
		Optional<Voyage> result;
		try {
			result = this.voyageRepository.findByCarrierIdAndVoyageId(carrier.getId(), voyageId);
		}
		catch (IllegalStateException ex) {
			return "voyage-error" + (fragment ? " :: fragment" : "");
		}
		if (result.isEmpty()) {
			return "voyage-not-found" + (fragment ? " :: fragment" : "");
		}
		result.ifPresent(voyage -> {
			model.addAttribute("voyage", voyage);
			model.addAttribute("delayLevel", calculateDelayLevel(voyage.getCurrentStation()));
		});
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
