/*
 * Copyright 2020 the original author or authors.
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

package io.github.vpavic.traintracker.infrastructure.security;

import java.time.Instant;

import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEventListener {

    private final JdbcOperations jdbcOperations;

    public AuthenticationEventListener(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @EventListener(InteractiveAuthenticationSuccessEvent.class)
    public void onInteractiveAuthenticationSuccessEvent(InteractiveAuthenticationSuccessEvent event) {
        OAuth2AuthenticationToken authentication = (OAuth2AuthenticationToken) event.getSource();
        OAuth2User principal = authentication.getPrincipal();
        long now = Instant.now().toEpochMilli();
        this.jdbcOperations.update(
                "INSERT INTO user_profile (email, first_login, last_login) VALUES (?, ?, ?) ON CONFLICT (email) DO UPDATE SET last_login = ?",
                principal.getName(), now, now, now);
    }

}
