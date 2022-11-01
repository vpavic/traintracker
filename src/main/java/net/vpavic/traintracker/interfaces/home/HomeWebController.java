package net.vpavic.traintracker.interfaces.home;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.vpavic.traintracker.domain.model.agency.Agencies;

@Controller
@RequestMapping(path = "/web", produces = MediaType.TEXT_HTML_VALUE)
class HomeWebController {

	@GetMapping
	String home() {
		return "redirect:/web/" + Agencies.getAll().keySet().iterator().next();
	}

}
