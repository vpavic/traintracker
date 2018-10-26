package io.traintracker.interfaces.voyage;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
class UnsupportedCountryException extends RuntimeException {

	UnsupportedCountryException(String country) {
		super("Unsupported country " + country);
	}

}
