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

package io.github.vpavic.traintracker.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter.ReferrerPolicy;

@Configuration(proxyBeanMethods = false)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity //
                .authorizeRequests(requests -> requests.anyRequest().permitAll()) //
                .oauth2Login(login -> login.loginPage("/")) //
                .headers(configurer -> {
                    configurer.httpStrictTransportSecurity();
                    configurer.contentSecurityPolicy("default-src https: 'self'; img-src https: data: 'self'; "
                            + "script-src https: 'self' 'sha256-CW5fKI3jV5qwoLYOgIrchxDYRW5DcBigpTBsFB6U8i8='");
                    configurer.referrerPolicy(ReferrerPolicy.NO_REFERRER_WHEN_DOWNGRADE);
                    configurer.featurePolicy("accelerometer 'none'; ambient-light-sensor 'none'; camera 'none'; "
                            + "encrypted-media 'none'; fullscreen 'none'; geolocation 'none'; gyroscope 'none'; "
                            + "magnetometer 'none'; microphone 'none'; midi 'none'; payment 'none'; speaker 'none'; "
                            + "sync-xhr 'none'; usb 'none'; vr 'none'");
                });
    }

}
