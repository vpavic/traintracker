/*
 * Copyright 2019 the original author or authors.
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

package io.traintracker.infrastructure.fetcher.hr

import org.apache.http.HttpEntity
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.client.methods.HttpUriRequest
import org.apache.http.entity.FileEntity
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.CloseableHttpClient
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock
import org.mockito.Mockito.reset
import java.io.File

class HrVoyageFetcherTests {
    private val httpClient = mock(CloseableHttpClient::class.java)

    private var voyageFetcher: HrVoyageFetcher? = null

    @Before
    fun setUp() {
        reset(this.httpClient)
        this.voyageFetcher = HrVoyageFetcher(this.httpClient)
    }

    @Test
    fun getCountry_ShouldReturnHr() {
        assertThat(this.voyageFetcher?.getCountry()).isEqualTo("hr")
    }

    @Test
    @Throws(Exception::class)
    fun getVoyage_VoyageDoesNotExist_ShouldReturnNull() {
        val response = mock(CloseableHttpResponse::class.java)
        given<HttpEntity>(response.entity).willReturn(StringEntity("<html/>"))
        given(this.httpClient.execute(any<HttpUriRequest>())).willReturn(response)

        assertThat(this.voyageFetcher?.getVoyage("123")).isNull()
    }

    @Test
    @Throws(Exception::class)
    fun getVoyage_VoyageExists_ShouldReturnVoyage() {
        val response = mock(CloseableHttpResponse::class.java)
        val responseHtml = File(javaClass.getResource("/hr-tpvl-ok.html").toURI())
        given<HttpEntity>(response.entity).willReturn(FileEntity(responseHtml))
        given(this.httpClient.execute(any<HttpUriRequest>())).willReturn(response)

        val voyage = this.voyageFetcher?.getVoyage("211")

        assertThat(voyage).isNotNull
        assertThat(voyage?.carrier?.id).isEqualTo("hr")
    }
}
