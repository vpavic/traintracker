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

package io.github.vpavic.traintracker.interfaces.voyage.cli

import de.vandermeer.asciitable.AsciiTable
import de.vandermeer.asciitable.CWC_LongestLine
import de.vandermeer.asciithemes.TA_GridThemes
import io.github.vpavic.traintracker.application.VoyageFetcher
import io.github.vpavic.traintracker.domain.model.voyage.Station
import io.github.vpavic.traintracker.domain.model.voyage.Voyage
import java.time.LocalTime
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/{country:[a-z]{2}}/{train}"], produces = [MediaType.TEXT_PLAIN_VALUE])
class VoyageCliController {

    @GetMapping
    fun voyage(@PathVariable("country") fetcher: VoyageFetcher?, @PathVariable train: String): String {
        if (fetcher == null) {
            return generateErrorReport("Unsupported country")
        }
        val voyage = fetcher.getVoyage(train) ?: return generateErrorReport("Voyage not found")
        return generateReport(train, voyage)
    }

    companion object {

        private fun generateErrorReport(error: String): String {
            val at = AsciiTable()
            at.addRow("Error: $error")
            return renderAsciiTable(at)
        }

        private fun generateReport(train: String, voyage: Voyage): String {
            val currentStation = voyage.currentStation
            val carrier = voyage.carrier
            val at = AsciiTable()
            at.addRule()
            at.addRow(null, null, "Report for train " + carrier.id + "/" + train)
            at.addRule()
            at.addRow("Current station", "Arrival", "Departure")
            at.addRow(currentStation.name, formatArrival(currentStation), formatDeparture(currentStation))
            at.addRule()
            at.addRow(null, null, "Generated on " + voyage.generatedTime + " " + carrier.timezone)
            at.addRule()
            return renderAsciiTable(at)
        }

        private fun formatArrival(station: Station): String {
            return formatTimeAndDelay(station.arrivalTime, station.arrivalDelay)
        }

        private fun formatDeparture(station: Station): String {
            return formatTimeAndDelay(station.departureTime, station.departureDelay)
        }

        private fun formatTimeAndDelay(time: LocalTime?, delay: Int): String {
            if (time == null) {
                return "-"
            }
            var timeAndDelay = time.toString()
            if (delay > 0) {
                timeAndDelay += " +$delay"
            }
            return timeAndDelay
        }

        private fun renderAsciiTable(at: AsciiTable): String {
            at.context.setGridTheme(TA_GridThemes.NONE)
            at.renderer.cwc = CWC_LongestLine()
            at.setPaddingLeftRight(1)
            return """
                ${at.render()}

                """.trimIndent()
        }
    }
}
