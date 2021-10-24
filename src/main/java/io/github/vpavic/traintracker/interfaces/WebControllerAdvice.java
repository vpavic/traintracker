package io.github.vpavic.traintracker.interfaces;

import java.util.Collection;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import io.github.vpavic.traintracker.domain.model.carrier.Carrier;
import io.github.vpavic.traintracker.domain.model.carrier.Carriers;

@ControllerAdvice
class WebControllerAdvice {

    @ModelAttribute("carriers")
    Collection<Carrier> carriers() {
        return Carriers.getAll().values();
    }

}
