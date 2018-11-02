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

package io.traintracker.interfaces;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import io.traintracker.application.VoyageFetcherResolver;
import io.traintracker.interfaces.home.HomeWebController;
import io.traintracker.interfaces.voyage.web.VoyageWebController;

@ControllerAdvice(assignableTypes = { HomeWebController.class, VoyageWebController.class })
public class WebControllerAdvice {

    private final VoyageFetcherResolver resolver;

    private final Map<String, String> logins;

    public WebControllerAdvice(VoyageFetcherResolver resolver) {
        Objects.requireNonNull(resolver, "VoyageFetcherResolver must not be null");
        this.resolver = resolver;
        this.logins = prepareLogins();
    }

    @SuppressWarnings("unchecked")
    private static Map<String, String> prepareLogins() {
        return Stream.of("google").collect(Collectors.toMap(s -> s, s -> "/oauth2/authorization/" + s));
    }

    @ModelAttribute("countries")
    public Set<String> countries() {
        return this.resolver.supportedCountries();
    }

    @ModelAttribute("logins")
    public Map<String, String> logins() {
        return this.logins;
    }

    @ModelAttribute("principal")
    public OAuth2User authentication(@AuthenticationPrincipal OAuth2User principal) {
        return principal;
    }

}
