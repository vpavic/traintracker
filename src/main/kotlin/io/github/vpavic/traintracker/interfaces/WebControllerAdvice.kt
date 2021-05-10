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

package io.github.vpavic.traintracker.interfaces

import io.github.vpavic.traintracker.interfaces.home.HomeWebController
import io.github.vpavic.traintracker.interfaces.voyage.web.VoyageWebController
import java.util.stream.Collectors
import java.util.stream.Stream
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ModelAttribute

@ControllerAdvice(assignableTypes = [HomeWebController::class, VoyageWebController::class])
class WebControllerAdvice {

    private val logins: Map<String, String> = prepareLogins()

    @ModelAttribute("countries")
    fun countries(): Set<String> {
        return COUNTRIES
    }

    @ModelAttribute("logins")
    fun logins(): Map<String, String> {
        return logins
    }

    @ModelAttribute("principal")
    fun authentication(@AuthenticationPrincipal principal: OAuth2User): OAuth2User {
        return principal
    }

    companion object {

        private val COUNTRIES = setOf("hr")

        private fun prepareLogins(): Map<String, String> {
            return Stream.of("google").collect(
                Collectors.toMap({ s: String -> s }, { s: String -> "/oauth2/authorization/$s" })
            )
        }
    }
}
