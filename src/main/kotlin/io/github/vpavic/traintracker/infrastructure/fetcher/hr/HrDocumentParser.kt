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

import io.github.vpavic.traintracker.domain.model.voyage.Station
import org.jsoup.nodes.Document
import java.time.LocalTime
import java.util.ArrayDeque
import java.util.Deque

internal object HrDocumentParser {

    @JvmStatic
    fun parseCurrentPosition(doc: Document): Station? {
        var station: Station? = null
        val tables = doc.getElementsByTag("tbody")
        if (tables.size == 2) {
            val rows = tables[1].children()
            val nameRaw = rows[1].child(0).child(1).text().trim { it <= ' ' }
            val position = rows[2]
            val direction = position.child(0).child(0).text().trim { it <= ' ' }
            val timeRaw = position.child(0).child(1).text().trim { it <= ' ' }
            val delayRaw = rows[3].child(0).child(0).child(0).text().trim { it <= ' ' }
            val name = nameRaw.replace('+', ' ')
            val time = LocalTime.parse(timeRaw.substring(12, 17))
            val delay = if (delayRaw.startsWith("Kasni")) delayRaw.substring(6, delayRaw.indexOf(' ', 6)).toInt() else 0
            if (direction == "Odlazak") {
                station = Station(name)
                station.departureTime = time
                station.departureDelay = delay
            } else if (direction == "Dolazak" || direction == "Završio vožnju") {
                station = Station(name)
                station.arrivalTime = time
                station.arrivalDelay = delay
            }
        }
        return station
    }

    @JvmStatic
    fun parseOverview(doc: Document): Deque<Station> {
        val stations: Deque<Station> = ArrayDeque()
        val tables = doc.getElementsByTag("tbody")
        if (tables.size == 3) {
            val rows = tables[2].children()
            rows.removeAt(0)
            for (row in rows) {
                val name = row.child(0).text().trim { it <= ' ' }
                val direction = row.child(1).text().trim { it <= ' ' }
                val time = row.child(3).text().trim { it <= ' ' }
                val delay = row.child(4).text().trim { it <= ' ' }
                var station: Station
                if (direction == "Dolazak") {
                    station = Station(name)
                    station.arrivalTime = LocalTime.parse(time)
                    station.arrivalDelay = if (delay.isEmpty()) 0 else delay.toInt()
                } else {
                    station = if (!stations.isEmpty() && stations.peekLast().name == name) {
                        stations.removeLast()
                    } else {
                        Station(name)
                    }
                    station.departureTime = LocalTime.parse(time)
                    station.departureDelay = if (delay.isEmpty()) 0 else delay.toInt()
                }
                stations.add(station)
            }
        }
        return stations
    }
}
