package io.vpavic.traintracker.infrastructure.fetcher.hzpp;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.vpavic.traintracker.domain.model.carrier.Carriers;
import io.vpavic.traintracker.domain.model.voyage.Voyage;

import static org.assertj.core.api.Assertions.assertThat;
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
        assertThat(this.voyageFetcher.getCarrier()).isEqualTo(Carriers.hzpp);
    }

    @Test
    void getVoyage_VoyageDoesNotExist_ShouldReturnNull() throws Exception {
        given((this.httpResponse.body())).willReturn("<html/>");
        given(this.httpClient.<String>send(any(), any())).willReturn(this.httpResponse);
        assertThat(this.voyageFetcher.getVoyage("123")).isNull();
    }

    @Test
    void getVoyage_VoyageExists_ShouldReturnVoyage() throws Exception {
        given(this.httpResponse.body()).willReturn(HzppSampleResponses.currentPositionOk);
        given(this.httpClient.<String>send(any(), any())).willReturn(this.httpResponse);
        Voyage voyage = this.voyageFetcher.getVoyage("211");
        assertThat(voyage).isNotNull();
        assertThat(voyage.getCarrierId()).isEqualTo(Carriers.hzpp.getId());
    }

}
