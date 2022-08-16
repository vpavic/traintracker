package xyz.vpavic.traintracker.interfaces;

import java.util.Collection;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import xyz.vpavic.traintracker.domain.model.agency.Agencies;
import xyz.vpavic.traintracker.domain.model.agency.Agency;

@ControllerAdvice
class WebControllerAdvice {

	@ModelAttribute("agencies")
	Collection<Agency> agencies() {
		return Agencies.getAll().values();
	}

}
