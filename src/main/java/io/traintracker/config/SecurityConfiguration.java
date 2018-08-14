package io.traintracker.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
			.authorizeRequests()
				.anyRequest().permitAll()
				.and()
			.oauth2Login()
				.and()
			.headers()
				.httpStrictTransportSecurity()
					.and()
				.contentSecurityPolicy("default-src https: 'self'; img-src https: data: 'self'; "
						+ "script-src https: 'self' 'sha256-GV92UCCdl4ev/6K7wyHn39c/5lPZO6fNFOTT3dDz4L4='");
		// @formatter:on
	}

}
