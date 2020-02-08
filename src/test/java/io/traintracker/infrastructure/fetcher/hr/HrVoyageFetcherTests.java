/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.traintracker.infrastructure.fetcher.hr;

import java.io.File;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Before;
import org.junit.Test;

import io.traintracker.domain.model.voyage.Voyage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;

public class HrVoyageFetcherTests {

    private CloseableHttpClient httpClient = mock(CloseableHttpClient.class);

    private HrVoyageFetcher voyageFetcher;

    @Before
    public void setUp() {
        reset(this.httpClient);
        this.voyageFetcher = new HrVoyageFetcher(this.httpClient);
    }

    @Test
    public void getCountry_ShouldReturnHr() {
        assertThat(this.voyageFetcher.getCountry()).isEqualTo("hr");
    }

    @Test
    public void getVoyage_VoyageDoesNotExist_ShouldReturnNull() throws Exception {
        CloseableHttpResponse response = mock(CloseableHttpResponse.class);
        given(response.getEntity()).willReturn(new StringEntity("<html/>"));
        given(this.httpClient.execute(any())).willReturn(response);
        assertThat(this.voyageFetcher.getVoyage("123")).isNull();
    }

    @Test
    public void getVoyage_VoyageExists_ShouldReturnVoyage() throws Exception {
        CloseableHttpResponse response = mock(CloseableHttpResponse.class);
        File responseHtml = new File(getClass().getResource("/hr-tpvl-ok.html").toURI());
        given(response.getEntity()).willReturn(new FileEntity(responseHtml));
        given(this.httpClient.execute(any())).willReturn(response);
        Voyage voyage = this.voyageFetcher.getVoyage("211");
        assertThat(voyage).isNotNull();
        assertThat(voyage.getCarrier().getId()).isEqualTo("hr");
    }

}
