package io.github.vpavic.traintracker.interfaces.home;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import io.github.vpavic.traintracker.application.VoyageFetcher;

@Controller
@RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
class HomeWebController {

    @GetMapping(path = "/")
    String home(@ModelAttribute("countries") Set<String> countries) {
        return "redirect:/" + countries.iterator().next() + "/";
    }

    @GetMapping(path = "/{country:[a-z]{2}}")
    String country(@PathVariable String country) {
        return "redirect:/" + country + "/";
    }

    @GetMapping(path = "/{country:[a-z]{2}}/")
    String country(@PathVariable("country") VoyageFetcher fetcher, Model model) {
        if (fetcher == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        model.addAttribute("country", fetcher.getCountry());
        return "home";
    }

}
