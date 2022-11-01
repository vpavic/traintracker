package net.vpavic.traintracker.interfaces;

import java.util.Collection;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import net.vpavic.traintracker.domain.model.agency.Agencies;
import net.vpavic.traintracker.domain.model.agency.Agency;

@ControllerAdvice
class WebControllerAdvice {

	@ModelAttribute("agencies")
	Collection<Agency> agencies() {
		return Agencies.getAll().values();
	}

}
