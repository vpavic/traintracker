/*
 * Copyright 2018 the original author or authors.
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

package io.traintracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter.ReferrerPolicy;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private final JdbcOperations jdbcOperations;

	public SecurityConfiguration(JdbcOperations jdbcOperations) {
		this.jdbcOperations = jdbcOperations;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
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
				.contentSecurityPolicy("default-src https: 'self'; img-src https: data: 'self'; "
						+ "script-src https: 'self' 'sha256-F+rFUB1Z7u0McuUUop6hGovMsPsMKIUuvSl3490LveA='")
					.and()
				.referrerPolicy(ReferrerPolicy.NO_REFERRER_WHEN_DOWNGRADE)
					.and()
				.featurePolicy("accelerometer 'none'; ambient-light-sensor 'none'; camera 'none'; "
						+ "encrypted-media 'none'; fullscreen 'none'; geolocation 'none'; "
						+ "gyroscope 'none'; magnetometer 'none'; microphone 'none'; midi 'none'; "
						+ "payment 'none'; speaker 'none'; sync-xhr 'none'; usb 'none'; vr 'none'");
		// @formatter:on
	}

	@Bean
	public ClientRegistrationRepository clientRegistrationRepository() {
		return registrationId -> {
			if (!"google".equals(registrationId)) {
				return null;
			}
			return this.jdbcOperations.queryForObject(
					"SELECT client_id, client_secret FROM client_registration WHERE id = ?",
					(rs, rowNum) -> CommonOAuth2Provider.valueOf(registrationId.toUpperCase())
							.getBuilder(registrationId).clientId(rs.getString("client_id"))
							.clientSecret(rs.getString("client_secret")).build(),
					registrationId);
		};
	}

}
