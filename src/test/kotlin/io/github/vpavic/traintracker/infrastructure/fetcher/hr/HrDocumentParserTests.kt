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

import java.io.File
import java.time.LocalTime
import org.assertj.core.api.Assertions.assertThat
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.junit.jupiter.api.Test

class HrDocumentParserTests {

    @Test
    fun `Assert that a valid train movement overview document is parsed correctly`() {
        val stations = HrDocumentParser.parseOverview(getDocument("/hr-pkvl-ok.html"))
        assertThat(stations).hasSize(35)
        val currentStation = stations.last
        assertThat(currentStation.name).isEqualTo("VINKOVCI")
        assertThat(currentStation.arrivalTime).isEqualTo(LocalTime.of(21, 28))
        assertThat(currentStation.arrivalDelay).isEqualTo(21)
        assertThat(currentStation.departureTime).isNull()
        assertThat(currentStation.departureDelay).isNull()
    }

    @Test
    fun `Assert that a train movement overview document for an unknown train is parsed correctly`() {
        val stations = HrDocumentParser.parseOverview(getDocument("/hr-pkvl-not-found.html"))
        assertThat(stations).isEmpty()
    }

    @Test
    fun `Assert that a valid train position document is parsed correctly`() {
        val station = HrDocumentParser.parseCurrentPosition(getDocument("/hr-tpvl-ok.html"))
        assertThat(station).isNotNull
        assertThat(station.name).isEqualTo("NOVA KAPELA BATRINA")
        assertThat(station.arrivalTime).isNull()
        assertThat(station.arrivalDelay).isNull()
        assertThat(station.departureTime).isEqualTo(LocalTime.of(5, 43))
        assertThat(station.departureDelay).isEqualTo(27)
    }

    @Test
    fun `Assert that a train position document for am unknown train is parsed correctly`() {
        val station = HrDocumentParser.parseCurrentPosition(getDocument("/hr-tpvl-not-found.html"))
        assertThat(station).isNull()
    }

    private fun getDocument(path: String): Document {
        val uri = javaClass.getResource(path).toURI()
        return Jsoup.parse(File(uri), "Cp1250")
    }
}
