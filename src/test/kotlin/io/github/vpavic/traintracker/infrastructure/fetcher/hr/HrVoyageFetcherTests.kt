/*
 * Copyright 2021 the original author or authors.
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

package io.github.vpavic.traintracker.infrastructure.fetcher.hr

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.entity.FileEntity
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.CloseableHttpClient
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File

class HrVoyageFetcherTests {

    @MockK
    private lateinit var httpClient: CloseableHttpClient

    @InjectMockKs
    private lateinit var voyageFetcher: HrVoyageFetcher

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `Assert that voyage fetcher country is Croatia`() {
        assertThat(voyageFetcher.country).isEqualTo("hr")
    }

    @Test
    fun `Assert that fetching an unknown train returns null`() {
        val response = mockk<CloseableHttpResponse>()
        every { response.entity } returns StringEntity("<html/>")
        every { httpClient.execute(any()) } returns response
        assertThat(voyageFetcher.getVoyage("123")).isNull()
    }

    @Test
    fun `Assert that fetching a valid train returns voyage`() {
        val response = mockk<CloseableHttpResponse>()
        every { response.entity } returns FileEntity(File(javaClass.getResource("/hr-tpvl-ok.html").toURI()))
        every { httpClient.execute(any()) } returns response
        val voyage = voyageFetcher.getVoyage("211")
        assertThat(voyage).isNotNull
        assertThat(voyage?.carrier?.id).isEqualTo("hr")
    }
}
