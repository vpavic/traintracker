package io.traintracker.interfaces;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
class UnsupportedCountryException extends RuntimeException {
}
