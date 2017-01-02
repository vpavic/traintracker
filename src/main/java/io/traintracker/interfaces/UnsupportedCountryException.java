package io.traintracker.interfaces;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
class UnsupportedCountryException extends RuntimeException {

	UnsupportedCountryException() {
		super("Unsupported country");
	}

}
