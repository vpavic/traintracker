package xyz.vpavic.traintracker.interfaces.agency;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import xyz.vpavic.traintracker.domain.model.agency.Agencies;
import xyz.vpavic.traintracker.domain.model.agency.Agency;
import xyz.vpavic.traintracker.domain.model.agency.AgencyId;

@Controller
@RequestMapping(path = "/web/{agencyId}", produces = MediaType.TEXT_HTML_VALUE)
class AgencyWebController {

	@GetMapping
	String getAgency(@PathVariable AgencyId agencyId, Model model) {
		Agency agency = Agencies.getById(agencyId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		model.addAttribute("agency", agency);
		return "agency";
	}

}
