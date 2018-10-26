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

package io.traintracker.interfaces.voyage;

import java.time.LocalTime;
import java.util.Objects;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciitable.CWC_LongestLine;
import de.vandermeer.asciithemes.TA_GridThemes;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.traintracker.application.VoyageFetcher;
import io.traintracker.application.VoyageFetcherResolver;
import io.traintracker.domain.model.carrier.Carrier;
import io.traintracker.domain.model.voyage.Station;
import io.traintracker.domain.model.voyage.Voyage;

@RestController
public class CliController {

	private final VoyageFetcherResolver resolver;

	public CliController(VoyageFetcherResolver resolver) {
		this.resolver = Objects.requireNonNull(resolver, "VoyageFetcherResolver must not be null");
	}

	@GetMapping(path = "/{country:[a-z]{2}}/{train}", produces = MediaType.TEXT_PLAIN_VALUE)
	public String voyage(@PathVariable String country, @PathVariable String train) {
		VoyageFetcher fetcher = this.resolver.getVoyageFetcher(country);
		if (fetcher == null) {
			return generateErrorReport("Unsupported country");
		}
		Voyage voyage = fetcher.getVoyage(train);
		if (voyage == null) {
			return generateErrorReport("Voyage not found");
		}
		return generateReport(train, voyage);
	}

	private static String generateErrorReport(String error) {
		AsciiTable at = new AsciiTable();
		at.addRow("Error: " + error);
		return renderAsciiTable(at);
	}

	private static String generateReport(String train, Voyage voyage) {
		Station currentStation = voyage.getCurrentStation();
		Carrier carrier = voyage.getCarrier();
		AsciiTable at = new AsciiTable();
		at.addRule();
		at.addRow(null, null, "Report for train " + carrier.getId() + "/" + train);
		at.addRule();
		at.addRow("Current station", "Arrival", "Departure");
		at.addRow(currentStation.getName(), formatArrival(currentStation), formatDeparture(currentStation));
		at.addRule();
		at.addRow(null, null, "Generated on " + voyage.getGeneratedTime() + " " + carrier.getTimezone());
		at.addRule();
		return renderAsciiTable(at);
	}

	private static String formatArrival(Station station) {
		return formatTimeAndDelay(station.getArrivalTime(), station.getArrivalDelay());
	}

	private static String formatDeparture(Station station) {
		return formatTimeAndDelay(station.getDepartureTime(), station.getDepartureDelay());
	}

	private static String formatTimeAndDelay(LocalTime time, Integer delay) {
		if (time == null) {
			return "-";
		}
		String timeAndDelay = time.toString();
		if (delay > 0) {
			timeAndDelay += " +" + delay;
		}
		return timeAndDelay;
	}

	private static String renderAsciiTable(AsciiTable at) {
		at.getContext().setGridTheme(TA_GridThemes.NONE);
		at.getRenderer().setCWC(new CWC_LongestLine());
		at.setPaddingLeftRight(1);
		return at.render() + '\n';
	}

}
