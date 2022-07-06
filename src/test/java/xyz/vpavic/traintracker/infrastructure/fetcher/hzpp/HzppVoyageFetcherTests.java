package xyz.vpavic.traintracker.infrastructure.fetcher.hzpp;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import xyz.vpavic.traintracker.domain.model.carrier.Carrier;
import xyz.vpavic.traintracker.domain.model.carrier.Carriers;
import xyz.vpavic.traintracker.domain.model.voyage.Voyage;
import xyz.vpavic.traintracker.domain.model.voyage.VoyageId;

import static org.assertj.core.api.BDDAssertions.catchIllegalStateException;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

/**
 * Tests for {@link HzppVoyageFetcher}.
 */
@DisplayName("HZPP VoyageFetcher")
@ExtendWith(MockitoExtension.class)
class HzppVoyageFetcherTests {

	@Nested
	@DisplayName("when #getCarrier")
	class WhenGetCarrier {

		@Test
		void thenReturnsHzpp() {
			// given
			HzppVoyageFetcher voyageFetcher = new HzppVoyageFetcher();
			// when
			Carrier carrier = voyageFetcher.getCarrier();
			// then
			then(carrier).as("Carrier").isEqualTo(Carriers.hzpp);
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
			given(this.httpClient.<String>send(any(), any())).willReturn(this.httpResponse);
			// when
			Optional<Voyage> result = this.voyageFetcher.getVoyage(VoyageId.of("123"));
			// then
			then(result).as("Voyage").isEmpty();
			BDDMockito.then(this.httpClient).should().send(any(), any());
			BDDMockito.then(this.httpClient).shouldHaveNoMoreInteractions();
		}

		@Test
		@DisplayName("given voyage found then returns Voyage")
		void givenVoyageFoundThenReturnsVoyage() throws Exception {
			// given
			VoyageId voyageId = VoyageId.of("544");
			given(this.httpResponse.body()).willReturn(HzppSampleResponses.currentPositionVoyageInProgress);
			given(this.httpClient.<String>send(any(), any())).willReturn(this.httpResponse);
			// when
			Optional<Voyage> result = this.voyageFetcher.getVoyage(voyageId);
			// then
			then(result).as("Voyage").hasValueSatisfying(voyage ->
					then(voyage.getId()).as("Voyage id").isEqualTo(voyageId));
			BDDMockito.then(this.httpClient).should().send(any(), any());
			BDDMockito.then(this.httpClient).shouldHaveNoMoreInteractions();
		}

		@Test
		@DisplayName("given connection error then throws exception")
		void givenConnectionErrorThenThrowsException() throws Exception {
			// given
			VoyageId voyageId = VoyageId.of("123");
			given(this.httpClient.send(any(), any())).willThrow(new IOException("test"));
			// when
			IllegalStateException exception = catchIllegalStateException(() -> this.voyageFetcher.getVoyage(voyageId));
			// then
			then(exception.getMessage()).as("Exception message").isEqualTo("test");
			BDDMockito.then(this.httpClient).should().send(any(), any());
			BDDMockito.then(this.httpClient).shouldHaveNoMoreInteractions();
		}

	}

}
