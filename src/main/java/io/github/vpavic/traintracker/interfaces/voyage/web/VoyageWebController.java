package io.github.vpavic.traintracker.interfaces.voyage.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import io.github.vpavic.traintracker.application.VoyageFetcher;
import io.github.vpavic.traintracker.domain.model.voyage.Station;
import io.github.vpavic.traintracker.domain.model.voyage.Voyage;

@Controller
@RequestMapping(path = "/{country:[a-z]{2}}/{train}", produces = MediaType.TEXT_HTML_VALUE)
class VoyageWebController {

    @GetMapping
    String voyage(@PathVariable("country") VoyageFetcher fetcher, @PathVariable String train,
            @RequestHeader(name = "X-PJAX", required = false) boolean pjax, Model model) {
        if (fetcher == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        model.addAttribute("country", fetcher.getCountry());
        model.addAttribute("train", train);
        Voyage voyage = fetcher.getVoyage(train);
        if (voyage == null) {
            return "not-found" + (pjax ? " :: fragment" : "");
        }
        model.addAttribute("delayLevel", calculateDelayLevel(voyage.getCurrentStation()));
        model.addAttribute("voyage", voyage);
        return "voyage" + (pjax ? " :: fragment" : "");
    }

    private String calculateDelayLevel(Station station) {
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
