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

package io.traintracker.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter.ReferrerPolicy

@Configuration
@EnableConfigurationProperties(SecurityProperties::class)
class SecurityConfiguration(val properties: SecurityProperties) : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity) {
        // @formatter:off
        http
            .authorizeRequests()
                .anyRequest().permitAll()
                .and()
            .oauth2Login()
                .loginPage("/")
                .and()
            .headers()
                .httpStrictTransportSecurity()
                    .and()
                .contentSecurityPolicy(
                    "default-src https: 'self'; img-src https: data: 'self'; " +
                        "script-src https: 'self' 'sha256-GSHdHk0+/vVwKMATSXq9jA0l/R72njl6b2I7JrLNt1Q='"
                )
                    .and()
                .referrerPolicy(ReferrerPolicy.NO_REFERRER_WHEN_DOWNGRADE)
                    .and()
                .featurePolicy(
                    "accelerometer 'none'; ambient-light-sensor 'none'; camera 'none'; " +
                        "encrypted-media 'none'; fullscreen 'none'; geolocation 'none'; " +
                        "gyroscope 'none'; magnetometer 'none'; microphone 'none'; midi 'none'; " +
                        "payment 'none'; speaker 'none'; sync-xhr 'none'; usb 'none'; vr 'none'"
                )
                    .and()
                .and()
            .requiresChannel()
                .anyRequest().requires(requiresChannel())
        // @formatter:on
    }

    private fun requiresChannel(): String {
        return if (this.properties.requireSecureChannel) "REQUIRES_SECURE_CHANNEL" else "REQUIRES_INSECURE_CHANNEL"
    }
}
