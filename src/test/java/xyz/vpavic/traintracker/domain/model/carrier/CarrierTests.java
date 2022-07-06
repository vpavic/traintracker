package xyz.vpavic.traintracker.domain.model.carrier;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.ZoneOffset;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.catchNullPointerException;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDSoftAssertions.thenSoftly;

/**
 * Tests for {@link Carrier}.
 */
@DisplayName("Carrier")
class CarrierTests {

	private static final CarrierId TEST_CARRIER_ID = CarrierId.of("test");

	private static final URL TEST_WEBSITE_URL = url("https://example.com/website");

	private static final URL TEST_LOGO_URL = url("https://example.com/logo");

	private static URL url(String spec) {
		try {
			return new URL(spec);
		}
		catch (MalformedURLException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Nested
	@DisplayName("when factory")
	class WhenFactory {

		@Test
		@DisplayName("given valid arguments then creates instance")
		void givenValidArgumentsThenCreatesInstance() {
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
		@DisplayName("given null id then throws exception")
		void givenNullIdThenThrowsException() {
			// when
			NullPointerException exception = catchNullPointerException(
					() -> Carrier.of(null, TEST_WEBSITE_URL, TEST_LOGO_URL, ZoneOffset.UTC));
			// then
			then(exception).as("Exception").hasMessage("id must not be null");
		}

		@Test
		@DisplayName("given null website then throws exception")
		void givenNullWebsiteThenThrowsException() {
			// when
			NullPointerException exception = catchNullPointerException(
					() -> Carrier.of(TEST_CARRIER_ID, null, TEST_LOGO_URL, ZoneOffset.UTC));
			// then
			then(exception).as("Exception").hasMessage("website must not be null");
		}

		@Test
		@DisplayName("given null logo then throws exception")
		void givenNullLogoThenThrowsException() {
			// when
			NullPointerException exception = catchNullPointerException(
					() -> Carrier.of(TEST_CARRIER_ID, TEST_WEBSITE_URL, null, ZoneOffset.UTC));
			// then
			then(exception).as("Exception").hasMessage("logo must not be null");
		}

		@Test
		@DisplayName("given null time zone then throws exception")
		void givenNullTimeZoneThenThrowsException() {
			// when
			NullPointerException exception = catchNullPointerException(
					() -> Carrier.of(TEST_CARRIER_ID, TEST_WEBSITE_URL, TEST_LOGO_URL, null));
			// then
			then(exception).as("Exception").hasMessage("timeZone must not be null");
		}

	}

}
