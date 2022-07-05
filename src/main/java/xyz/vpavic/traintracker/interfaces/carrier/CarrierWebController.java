package xyz.vpavic.traintracker.interfaces.carrier;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import xyz.vpavic.traintracker.domain.model.carrier.Carrier;
import xyz.vpavic.traintracker.domain.model.carrier.CarrierId;
import xyz.vpavic.traintracker.domain.model.carrier.Carriers;

@Controller
@RequestMapping(path = "/web/{carrierId:[a-z]+}", produces = MediaType.TEXT_HTML_VALUE)
class CarrierWebController {

	@GetMapping
	String getCarrier(@PathVariable CarrierId carrierId, Model model) {
		Carrier carrier = Carriers.getById(carrierId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		model.addAttribute("carrier", carrier);
		return "carrier";
	}

}