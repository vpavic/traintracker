package io.traintracker.app.interfaces;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.Iterables;
import io.traintracker.app.domain.Station;
import io.traintracker.app.application.VoyageService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Objects.requireNonNull;

@RestController
@RequestMapping(path = "/api/{country}/{train}")
public class ApiController {

	private final VoyageService voyageService;

	public ApiController(VoyageService voyageService) {
		this.voyageService = requireNonNull(voyageService);
	}

	@GetMapping
	public Voyage voyage(@PathVariable String country, @PathVariable String train)
			throws Exception {
		List<Station> stations = voyageService.getStations(country, train);
		return new Voyage(stations);
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Error error(Exception e) {
		return new Error(e.getMessage());
	}

	static class Voyage {

		private Station currentStation;

		private LocalTime generatedTime;

		private List<Station> stations;

		Voyage(List<Station> stations) {
			this.currentStation = Iterables.getLast(stations);
			this.generatedTime = LocalTime.now().truncatedTo(ChronoUnit.SECONDS);
			this.stations = stations;
		}

		public Station getCurrentStation() {
			return this.currentStation;
		}

		public LocalTime getGeneratedTime() {
			return this.generatedTime;
		}

		public Collection<Station> getStations() {
			return this.stations;
		}

	}

	static class Error {

		private final String message;

		Error(String message) {
			this.message = message;
		}

		public String getMessage() {
			return this.message;
		}

	}

}
