package io.traintracker.app.interfaces;

import io.traintracker.app.application.VoyageService;
import io.traintracker.app.domain.Voyage;
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
		Voyage voyage = this.voyageService.getVoyage(country, train);
		if (voyage == null) {
			throw new VoyageNotFoundException();
		}
		return voyage;
	}

	@ExceptionHandler(VoyageNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public void notFound() {
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Error error(Exception e) {
		return new Error(e.getMessage());
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
