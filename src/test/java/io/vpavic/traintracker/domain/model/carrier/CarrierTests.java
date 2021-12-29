package io.vpavic.traintracker.domain.model.carrier;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.catchThrowableOfType;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDSoftAssertions.thenSoftly;

class CarrierTests {

	private static final CarrierId TEST_CARRIER_ID = CarrierId.of("test");

	private static final URL TEST_WEBSITE_URL = url("https://example.com/website");

	private static final URL TEST_LOGO_URL = url("https://example.com/logo");

	@Test
	void of_ValidArguments_ShouldCreateCarrier() {
		// when
		Carrier test = Carrier.of(TEST_CARRIER_ID, TEST_WEBSITE_URL, TEST_LOGO_URL, ZoneOffset.UTC);
		// then
		thenSoftly(softly -> {
			softly.then(test.getId()).as("Carrier id").isEqualTo(TEST_CARRIER_ID);
			softly.then(test.getWebsite()).as("Carrier website").isEqualTo(TEST_WEBSITE_URL);
			softly.then(test.getLogo()).as("Carrier logo").isEqualTo(TEST_LOGO_URL);
			softly.then(test.getTimeZone()).as("Carrier time zone").isEqualTo(ZoneOffset.UTC);
		});
	}

	@Test
	void of_NullId_ShouldThrowException() {
		// when
		NullPointerException exception = catchThrowableOfType(
				() -> Carrier.of(null, TEST_WEBSITE_URL, TEST_LOGO_URL, ZoneOffset.UTC), NullPointerException.class);
		// then
		then(exception).as("Exception").hasMessage("id must not be null");
	}

	@Test
	void of_NullWebsite_ShouldThrowException() {
		// when
		NullPointerException exception = catchThrowableOfType(
				() -> Carrier.of(TEST_CARRIER_ID, null, TEST_LOGO_URL, ZoneOffset.UTC), NullPointerException.class);
		// then
		then(exception).as("Exception").hasMessage("website must not be null");
	}

	@Test
	void of_NullLogo_ShouldThrowException() {
		// when
		NullPointerException exception = catchThrowableOfType(
				() -> Carrier.of(TEST_CARRIER_ID, TEST_WEBSITE_URL, null, ZoneOffset.UTC), NullPointerException.class);
		// then
		then(exception).as("Exception").hasMessage("logo must not be null");
	}

	@Test
	void of_NullTimeZone_ShouldThrowException() {
		// when
		NullPointerException exception = catchThrowableOfType(
				() -> Carrier.of(TEST_CARRIER_ID, TEST_WEBSITE_URL, TEST_LOGO_URL, null), NullPointerException.class);
		// then
		then(exception).as("Exception").hasMessage("timeZone must not be null");
	}

	private static URL url(String spec) {
		try {
			return new URL(spec);
		}
		catch (MalformedURLException ex) {
			throw new RuntimeException(ex);
		}
	}

}
