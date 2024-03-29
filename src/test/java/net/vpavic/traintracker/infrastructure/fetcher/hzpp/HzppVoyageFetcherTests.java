package net.vpavic.traintracker.infrastructure.fetcher.hzpp;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import net.vpavic.traintracker.domain.model.agency.Agencies;
import net.vpavic.traintracker.domain.model.agency.Agency;
import net.vpavic.traintracker.domain.model.voyage.Voyage;
import net.vpavic.traintracker.domain.model.voyage.VoyageId;

import static org.assertj.core.api.BDDAssertions.catchIllegalStateException;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.BDDMockito.given;

/**
 * Tests for {@link HzppVoyageFetcher}.
 */
@DisplayName("HZPP VoyageFetcher")
@ExtendWith(MockitoExtension.class)
class HzppVoyageFetcherTests {

	@Nested
	@DisplayName("when #getAgency")
	class WhenGetAgency {

		@Test
		void thenReturnsHz() {
			// given
			HzppVoyageFetcher voyageFetcher = new HzppVoyageFetcher();
			// when
			Agency agency = voyageFetcher.getAgency();
			// then
			then(agency).as("Agency").isEqualTo(Agencies.hz);
		}

	}

	@Nested
	@DisplayName("when #getVoyage")
	class WhenGetVoyage {

		@Mock
		private HttpClient httpClient;

		@Mock
		private HttpResponse<String> httpResponse;

		private HzppVoyageFetcher voyageFetcher;

		@BeforeEach
		void setUp() {
			this.voyageFetcher = new HzppVoyageFetcher(this.httpClient);
		}

		@Test
		@DisplayName("given voyage not found then returns empty")
		void givenVoyageNotFoundThenReturnsEmpty() throws Exception {
			// given
			given((this.httpResponse.body())).willReturn(HzppSampleResponses.currentPositionNotFound);
			given(this.httpClient.<String>send(notNull(), notNull())).willReturn(this.httpResponse);
			// when
			Optional<Voyage> result = this.voyageFetcher.getVoyage(VoyageId.of("123"));
			// then
			then(result).as("Voyage").isEmpty();
		}

		@Test
		@DisplayName("given voyage found then returns Voyage")
		void givenVoyageFoundThenReturnsVoyage() throws Exception {
			// given
			VoyageId voyageId = VoyageId.of("544");
			given(this.httpResponse.body()).willReturn(HzppSampleResponses.currentPositionVoyageInProgress);
			given(this.httpClient.<String>send(notNull(), notNull())).willReturn(this.httpResponse);
			// when
			Optional<Voyage> result = this.voyageFetcher.getVoyage(voyageId);
			// then
			then(result).as("Voyage").hasValueSatisfying(voyage ->
					then(voyage.getId()).as("Voyage id").isEqualTo(voyageId));
		}

		@Test
		@DisplayName("given connection error then throws exception")
		void givenConnectionErrorThenThrowsException() throws Exception {
			// given
			VoyageId voyageId = VoyageId.of("123");
			given(this.httpClient.send(notNull(), notNull())).willThrow(new IOException("test"));
			// when
			IllegalStateException exception = catchIllegalStateException(() -> this.voyageFetcher.getVoyage(voyageId));
			// then
			then(exception.getMessage()).as("Exception message").isEqualTo("test");
		}

	}

}
