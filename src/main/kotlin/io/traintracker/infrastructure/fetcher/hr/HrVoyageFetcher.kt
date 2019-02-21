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

import io.traintracker.application.VoyageFetcher
import io.traintracker.domain.model.carrier.Carrier
import io.traintracker.domain.model.voyage.Voyage
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.utils.URIBuilder
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.util.EntityUtils
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component
import java.io.IOException
import java.net.URI
import java.net.URISyntaxException
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Component
class HrVoyageFetcher(private val httpClient: CloseableHttpClient) : VoyageFetcher {
    override fun getCountry(): String {
        return carrier.id
    }

    @Cacheable("voyages-hr")
    override fun getVoyage(train: String): Voyage? {
        val currentPositionRequestUri = buildCurrentPositionRequestUri(train)
        val currentPositionResponse = executeRequest(currentPositionRequestUri)
        val currentPositionDocument = parseDocument(currentPositionResponse)
        val currentStation = HrDocumentParser.parseCurrentPosition(currentPositionDocument) ?: return null

        val overviewRequestUri = buildOverviewRequestUri(train, LocalDate.now(carrier.timezone))
        val overviewResponse = executeRequest(overviewRequestUri)
        val doc = parseDocument(overviewResponse)
        val stations = HrDocumentParser.parseOverview(doc)

        return Voyage(currentStation, stations, carrier, overviewRequestUri.toString())
    }

    private fun executeRequest(uri: URI): CloseableHttpResponse {
        try {
            val request = HttpGet(uri)
            return this.httpClient.execute(request)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    private fun parseDocument(response: CloseableHttpResponse): Document {
        try {
            val html = EntityUtils.toString(response.entity, "Cp1250")
            return Jsoup.parse(html)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    companion object {
        private val carrier = Carrier(
            "hr", "HŽ Infrastruktura", "http://www.hzinfra.hr/",
            ZoneId.of("Europe/Zagreb")
        )

        private val formatter = DateTimeFormatter.ofPattern("uuMMdd")

        private fun buildOverviewRequestUri(train: String, date: LocalDate): URI {
            try {
                // @formatter:off
                return URIBuilder("http://najava.hzinfra.hr/hzinfo/default.asp")
                    .addParameter("vl", train)
                    .addParameter("d1", date.format(formatter))
                    .addParameter("category", "korisnici")
                    .addParameter("service", "pkvl")
                    .addParameter("screen", "2")
                    .build()
                // @formatter:on
            } catch (e: URISyntaxException) {
                throw RuntimeException(e)
            }
        }

        private fun buildCurrentPositionRequestUri(train: String): URI {
            try {
                // @formatter:off
                return URIBuilder("http://vred.hzinfra.hr/hzinfo/Default.asp")
                    .addParameter("vl", train)
                    .addParameter("category", "hzinfo")
                    .addParameter("service", "tpvl")
                    .addParameter("screen", "2")
                    .build()
                // @formatter:on
            } catch (e: URISyntaxException) {
                throw RuntimeException(e)
            }
        }
    }
}
