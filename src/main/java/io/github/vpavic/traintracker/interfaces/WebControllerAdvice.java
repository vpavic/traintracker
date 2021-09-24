package io.github.vpavic.traintracker.interfaces;

import java.util.Collections;
import java.util.Set;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
class WebControllerAdvice {

    private static final Set<String> COUNTRIES = Collections.singleton("hr");

    @ModelAttribute("countries")
    Set<String> countries() {
        return COUNTRIES;
    }

}
