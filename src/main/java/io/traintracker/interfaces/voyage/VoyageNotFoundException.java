package io.traintracker.interfaces.voyage;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
class VoyageNotFoundException extends RuntimeException {

	VoyageNotFoundException(String train) {
		super("Voyage " + train + " not found");
	}

}
