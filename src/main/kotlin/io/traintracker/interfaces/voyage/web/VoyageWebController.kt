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

package io.traintracker.interfaces.voyage.web

import io.traintracker.application.VoyageFetcher
import io.traintracker.domain.model.voyage.Station
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.server.ResponseStatusException

@Controller
@RequestMapping(path = ["/{country:[a-z]{2}}/{train}"], produces = [MediaType.TEXT_HTML_VALUE])
class VoyageWebController {
    @GetMapping
    fun voyage(
        @PathVariable("country") fetcher: VoyageFetcher?,
        @PathVariable train: String,
        @RequestHeader(name = "X-PJAX", required = false) pjax: Boolean,
        model: Model
    ): String {
        if (fetcher == null) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }
        model.addAttribute("country", fetcher.getCountry())
        model.addAttribute("train", train)
        val voyage = fetcher.getVoyage(train) ?: return "not-found" + if (pjax) " :: fragment" else ""
        model.addAttribute("delayLevel", calculateDelayLevel(voyage.currentStation))
        model.addAttribute("voyage", voyage)
        return "voyage" + if (pjax) " :: fragment" else ""
    }

    private fun calculateDelayLevel(station: Station): String {
        val delay = if (station.departureDelay != null) station.departureDelay else station.arrivalDelay
        return when {
            delay == null -> "info"
            delay < 5 -> "success"
            delay < 20 -> "warning"
            else -> "danger"
        }
    }
}
