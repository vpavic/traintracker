package net.vpavic.traintracker.domain.model.agency;

import java.net.URI;
import java.time.ZoneOffset;
import java.util.Locale;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.catchNullPointerException;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDSoftAssertions.thenSoftly;

/**
 * Tests for {@link Agency}.
 */
@DisplayName("Agency")
class AgencyTests {

	private static final AgencyId TEST_AGENCY_ID = AgencyId.of("test");

	private static final String TEST_AGENCY_NAME = "Test";

	private static final URI TEST_AGENCY_URL = URI.create("https://example.com/agency_url");

	private static final URI TEST_AGENCY_FARE_URL = URI.create("https://example.com/agency_fare_url");

	private static final String TEST_AGENCY_PHONE = "+1000000000";

	private static final String TEST_AGENCY_EMAIL = "agency@example.com";

	@Nested
	@DisplayName("when builder")
	class WhenBuilder {

		@Test
		@DisplayName("given valid arguments then creates instance")
		void givenValidArgumentsThenCreatesInstance() {
			// when
			Agency test = Agency.builder(TEST_AGENCY_ID, TEST_AGENCY_NAME, TEST_AGENCY_URL, ZoneOffset.UTC)
					.language(Locale.ENGLISH)
					.fareUrl(TEST_AGENCY_FARE_URL)
					.phone(TEST_AGENCY_PHONE)
					.email(TEST_AGENCY_EMAIL).build();
			// then
			thenSoftly(softly -> {
				softly.then(test.getId()).as("Agency id").isEqualTo(TEST_AGENCY_ID);
				softly.then(test.getName()).as("Agency name").isEqualTo(TEST_AGENCY_NAME);
				softly.then(test.getUrl()).as("Agency URL").isEqualTo(TEST_AGENCY_URL);
				softly.then(test.getTimezone()).as("Agency timezone").isEqualTo(ZoneOffset.UTC);
				softly.then(test.getLanguage()).as("Agency language").isEqualTo(Locale.ENGLISH);
				softly.then(test.getFareUrl()).as("Agency fare URL").isEqualTo(TEST_AGENCY_FARE_URL);
				softly.then(test.getPhone()).as("Agency phone").isEqualTo(TEST_AGENCY_PHONE);
				softly.then(test.getEmail()).as("Agency email").isEqualTo(TEST_AGENCY_EMAIL);
			});
		}

		@Test
		@DisplayName("given null id then throws exception")
		void givenNullIdThenThrowsException() {
			// when
			NullPointerException exception = catchNullPointerException(
					() -> Agency.builder(null, TEST_AGENCY_NAME, TEST_AGENCY_URL, ZoneOffset.UTC).build());
			// then
			then(exception).as("Exception").hasMessage("id must not be null");
		}

		@Test
		@DisplayName("given null name then throws exception")
		void givenNullLogoThenThrowsException() {
			// when
			NullPointerException exception = catchNullPointerException(
					() -> Agency.builder(TEST_AGENCY_ID, null, TEST_AGENCY_URL, ZoneOffset.UTC).build());
			// then
			then(exception).as("Exception").hasMessage("name must not be null");
		}

		@Test
		@DisplayName("given null website then throws exception")
		void givenNullWebsiteThenThrowsException() {
			// when
			NullPointerException exception = catchNullPointerException(
					() -> Agency.builder(TEST_AGENCY_ID, TEST_AGENCY_NAME, null, ZoneOffset.UTC).build());
			// then
			then(exception).as("Exception").hasMessage("url must not be null");
		}

		@Test
		@DisplayName("given null timezone then throws exception")
		void givenNullTimeZoneThenThrowsException() {
			// when
			NullPointerException exception = catchNullPointerException(
					() -> Agency.builder(TEST_AGENCY_ID, TEST_AGENCY_NAME, TEST_AGENCY_URL, null).build());
			// then
			then(exception).as("Exception").hasMessage("timezone must not be null");
		}

	}

}
