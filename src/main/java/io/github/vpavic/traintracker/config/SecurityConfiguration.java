package io.github.vpavic.traintracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;

@Configuration(proxyBeanMethods = false)
class SecurityConfiguration {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeRequests(requests -> requests.anyRequest().permitAll())
                .headers(headers -> {
                    headers.httpStrictTransportSecurity(Customizer.withDefaults());
                    headers.contentSecurityPolicy(config -> config.policyDirectives("default-src https: 'self'; " +
                            "img-src https: data: 'self'; script-src https: 'self' " +
                            "'sha256-CW5fKI3jV5qwoLYOgIrchxDYRW5DcBigpTBsFB6U8i8='"));
                    headers.referrerPolicy(config -> config.policy(
                            ReferrerPolicyHeaderWriter.ReferrerPolicy.NO_REFERRER_WHEN_DOWNGRADE));
                    headers.permissionsPolicy(config -> config.policy("accelerometer=(),autoplay=(),camera=()," +
                            "display-capture=(),document-domain=(),encrypted-media=(),fullscreen=(),geolocation=()," +
                            "gyroscope=(),magnetometer=(),microphone=(),midi=(),payment=(),picture-in-picture=()," +
                            "publickey-credentials-get=(),screen-wake-lock=(),sync-xhr=(self),usb=(),web-share=()," +
                            "xr-spatial-tracking=()"));
                })
                .build();
    }

}
