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

package io.github.vpavic.traintracker.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.web.servlet.invoke
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter

@Configuration(proxyBeanMethods = false)
class SecurityConfiguration : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http {
            authorizeRequests {
                authorize(anyRequest, permitAll)
            }
            headers {
                httpStrictTransportSecurity {}
                contentSecurityPolicy {
                    policyDirectives = "default-src https: 'self'; img-src https: data: 'self'; " +
                            "script-src https: 'self' 'sha256-CW5fKI3jV5qwoLYOgIrchxDYRW5DcBigpTBsFB6U8i8='"
                }
                referrerPolicy {
                    policy = ReferrerPolicyHeaderWriter.ReferrerPolicy.NO_REFERRER_WHEN_DOWNGRADE
                }
                featurePolicy(
                    "accelerometer 'none'; ambient-light-sensor 'none'; camera 'none'; encrypted-media 'none'; " +
                            "fullscreen 'none'; geolocation 'none'; gyroscope 'none'; magnetometer 'none'; " +
                            "microphone 'none'; midi 'none'; payment 'none'; speaker 'none'; sync-xhr 'none'; " +
                            "usb 'none'; vr 'none'"
                )
            }
        }
    }
}
