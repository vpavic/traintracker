package io.vpavic.traintracker.infrastructure.fetcher.hr;

import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

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
class HrVoyageFetcherTests {

    @Mock
    private HttpClient httpClient;

    @Mock
    private HttpResponse<String> httpResponse;

    private HrVoyageFetcher voyageFetcher;

    @BeforeEach
    void setUp() {
        this.voyageFetcher = new HrVoyageFetcher(this.httpClient);
    }

    @Test
    void getCountry_ShouldReturnHr() {
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
        URL resource = getClass().getClassLoader().getResource("hr-tpvl-ok.html");
        assertThat(resource).isNotNull();
        String responseBody = Files.readString(Path.of(resource.toURI()), Charset.forName("Cp1250"));
        given(this.httpResponse.body()).willReturn(responseBody);
        given(this.httpClient.<String>send(any(), any())).willReturn(this.httpResponse);
        Voyage voyage = this.voyageFetcher.getVoyage("211");
        assertThat(voyage).isNotNull();
        assertThat(voyage.getCarrier().getId()).isEqualTo(Carriers.hzpp.getId());
    }

}
