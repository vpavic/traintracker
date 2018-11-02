/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.traintracker.interfaces.voyage.web;

import java.util.Objects;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import io.traintracker.application.VoyageFetcher;
import io.traintracker.application.VoyageFetcherResolver;
import io.traintracker.domain.model.voyage.Station;
import io.traintracker.domain.model.voyage.Voyage;

@Controller
@RequestMapping(path = "/{country:[a-z]{2}}/{train}", produces = MediaType.TEXT_HTML_VALUE)
public class VoyageWebController {

    private final VoyageFetcherResolver resolver;

    public VoyageWebController(VoyageFetcherResolver resolver) {
        Objects.requireNonNull(resolver, "VoyageFetcherResolver must not be null");
        this.resolver = resolver;
    }

    @GetMapping
    public String voyage(@PathVariable String country, @PathVariable String train,
            @RequestHeader(name = "X-PJAX", required = false) boolean pjax, Model model) {
        model.addAttribute("country", country);
        VoyageFetcher fetcher = this.resolver.getVoyageFetcher(country);
        if (fetcher == null) {
            return "not-found" + (pjax ? " :: fragment" : "");
        }
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
        else if (delay < 1) {
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
