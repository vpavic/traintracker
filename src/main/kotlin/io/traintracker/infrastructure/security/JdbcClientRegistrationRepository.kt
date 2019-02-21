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

import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.JdbcOperations
import org.springframework.jdbc.core.RowMapper
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.stereotype.Component

@Component
class JdbcClientRegistrationRepository(private val jdbcOperations: JdbcOperations) : ClientRegistrationRepository {
    override fun findByRegistrationId(registrationId: String): ClientRegistration? {
        if ("google" != registrationId) {
            return null
        }
        return try {
            this.jdbcOperations.queryForObject<ClientRegistration>(
                "SELECT client_id, client_secret FROM client_registration WHERE id = ?",
                RowMapper { rs, _ ->
                    createClientRegistration(
                        registrationId, rs.getString("client_id"),
                        rs.getString("client_secret")
                    )
                },
                registrationId
            )
        } catch (e: EmptyResultDataAccessException) {
            null
        }
    }

    private fun createClientRegistration(
        registrationId: String,
        clientId: String,
        clientSecret: String
    ): ClientRegistration {
        // @formatter:off
        return CommonOAuth2Provider.valueOf(registrationId.toUpperCase())
            .getBuilder(registrationId)
            .clientId(clientId)
            .clientSecret(clientSecret)
            .userNameAttributeName("email")
            .build()
        // @formatter:on
    }
}
