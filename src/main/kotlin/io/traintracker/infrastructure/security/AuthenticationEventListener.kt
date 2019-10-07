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

package io.traintracker.infrastructure.security

import java.time.Instant
import org.springframework.context.event.EventListener
import org.springframework.jdbc.core.JdbcOperations
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.stereotype.Component

@Component
class AuthenticationEventListener(private val jdbcOperations: JdbcOperations) {
    @EventListener(InteractiveAuthenticationSuccessEvent::class)
    fun onInteractiveAuthenticationSuccessEvent(event: InteractiveAuthenticationSuccessEvent) {
        val authentication = event.source as OAuth2AuthenticationToken
        val principal = authentication.principal
        val now = Instant.now().toEpochMilli()
        this.jdbcOperations.update(
            "INSERT INTO user_profile (email, first_login, last_login) " +
                    "VALUES (?, ?, ?) ON CONFLICT (email) DO UPDATE SET last_login = ?",
            principal.name, now, now, now
        )
    }
}
