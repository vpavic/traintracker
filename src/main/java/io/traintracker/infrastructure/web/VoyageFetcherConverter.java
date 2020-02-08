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

package io.traintracker.infrastructure.web;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import io.traintracker.application.VoyageFetcher;

@Component
public class VoyageFetcherConverter implements Converter<String, VoyageFetcher> {

    private final Map<String, VoyageFetcher> fetchers;

    public VoyageFetcherConverter(List<VoyageFetcher> fetchers) {
        Map<String, VoyageFetcher> map = new HashMap<>();
        for (VoyageFetcher fetcher : fetchers) {
            map.put(fetcher.getCountry(), fetcher);
        }
        this.fetchers = Collections.unmodifiableMap(new TreeMap<>(map));
    }

    @Override
    public VoyageFetcher convert(@NonNull String source) {
        return this.fetchers.get(source);
    }

}
