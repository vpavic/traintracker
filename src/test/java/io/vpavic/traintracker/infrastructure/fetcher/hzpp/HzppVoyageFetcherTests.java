package io.vpavic.traintracker.infrastructure.fetcher.hzpp;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.vpavic.traintracker.domain.model.carrier.Carrier;
import io.vpavic.traintracker.domain.model.carrier.Carriers;
import io.vpavic.traintracker.domain.model.voyage.Voyage;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class HzppVoyageFetcherTests {

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
	void getCarrier_ShouldReturnHzpp() {
		// when
		Carrier carrier = this.voyageFetcher.getCarrier();
		// then
		then(carrier).as("Carrier").isEqualTo(Carriers.hzpp);
	}

	@Test
	void getVoyage_VoyageDoesNotExist_ShouldReturnNull() throws Exception {
		// given
		given((this.httpResponse.body())).willReturn("<html/>");
		given(this.httpClient.<String>send(any(), any())).willReturn(this.httpResponse);
		// when
		Optional<Voyage> voyage = this.voyageFetcher.getVoyage("123");
		// then
		then(voyage).as("Voyage").isEmpty();
		BDDMockito.then(this.httpClient).should().send(any(), any());
		BDDMockito.then(this.httpClient).shouldHaveNoMoreInteractions();
	}

	@Test
	void getVoyage_VoyageExists_ShouldReturnVoyage() throws Exception {
		// given
		given(this.httpResponse.body()).willReturn(HzppSampleResponses.currentPositionVoyageInProgress);
		given(this.httpClient.<String>send(any(), any())).willReturn(this.httpResponse);
		// when
		Optional<Voyage> voyage = this.voyageFetcher.getVoyage("211");
		// then
		then(voyage).as("Voyage").hasValueSatisfying(v ->
				then(v.getCarrierId()).as("Voyage carrier id").isEqualTo(Carriers.hzpp.getId()));
		BDDMockito.then(this.httpClient).should().send(any(), any());
		BDDMockito.then(this.httpClient).shouldHaveNoMoreInteractions();
	}

}
