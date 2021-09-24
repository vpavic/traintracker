package io.github.vpavic.traintracker.infrastructure.fetcher.hr;

import java.io.File;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.vpavic.traintracker.domain.model.voyage.Voyage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;

class HrVoyageFetcherTests {

    private CloseableHttpClient httpClient = mock(CloseableHttpClient.class);

    private HrVoyageFetcher voyageFetcher;

    @BeforeEach
    void setUp() {
        reset(this.httpClient);
        this.voyageFetcher = new HrVoyageFetcher(this.httpClient);
    }

    @Test
    void getCountry_ShouldReturnHr() {
        assertThat(this.voyageFetcher.getCountry()).isEqualTo("hr");
    }

    @Test
    void getVoyage_VoyageDoesNotExist_ShouldReturnNull() throws Exception {
        CloseableHttpResponse response = mock(CloseableHttpResponse.class);
        given(response.getEntity()).willReturn(new StringEntity("<html/>"));
        given(this.httpClient.execute(any())).willReturn(response);
        assertThat(this.voyageFetcher.getVoyage("123")).isNull();
    }

    @Test
    void getVoyage_VoyageExists_ShouldReturnVoyage() throws Exception {
        CloseableHttpResponse response = mock(CloseableHttpResponse.class);
        File responseHtml = new File(getClass().getResource("/hr-tpvl-ok.html").toURI());
        given(response.getEntity()).willReturn(new FileEntity(responseHtml));
        given(this.httpClient.execute(any())).willReturn(response);
        Voyage voyage = this.voyageFetcher.getVoyage("211");
        assertThat(voyage).isNotNull();
        assertThat(voyage.getCarrier().getId()).isEqualTo("hr");
    }

}
