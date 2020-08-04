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

package io.github.vpavic.traintracker.infrastructure.fetcher.hr;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Objects;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import io.github.vpavic.traintracker.application.VoyageFetcher;
import io.github.vpavic.traintracker.domain.model.carrier.Carrier;
import io.github.vpavic.traintracker.domain.model.voyage.Station;
import io.github.vpavic.traintracker.domain.model.voyage.Voyage;

@Component
public class HrVoyageFetcher implements VoyageFetcher {

    private static final Carrier carrier = new Carrier("hr", "HŽ Infrastruktura", "http://www.hzinfra.hr/",
            ZoneId.of("Europe/Zagreb"));

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuMMdd");

    private final CloseableHttpClient httpClient;

    public HrVoyageFetcher(CloseableHttpClient httpClient) {
        Objects.requireNonNull(httpClient, "httpClient must not be null");
        this.httpClient = httpClient;
    }

    @Override
    public String getCountry() {
        return carrier.getId();
    }

    @Override
    @Cacheable("voyages-hr")
    public Voyage getVoyage(String train) {
        URI currentPositionRequestUri = buildCurrentPositionRequestUri(train);
        CloseableHttpResponse currentPositionResponse = executeRequest(currentPositionRequestUri);
        Document currentPositionDocument = parseDocument(currentPositionResponse);
        Station currentStation = HrDocumentParser.parseCurrentPosition(currentPositionDocument);
        if (currentStation == null) {
            return null;
        }
        URI overviewRequestUri = buildOverviewRequestUri(train, LocalDate.now(carrier.getTimezone()));
        CloseableHttpResponse overviewResponse = executeRequest(overviewRequestUri);
        Document doc = parseDocument(overviewResponse);
        Deque<Station> stations = HrDocumentParser.parseOverview(doc);
        LocalDate time = LocalDate.now(carrier.getTimezone());
        LocalTime generatedTime = LocalTime.now(carrier.getTimezone());
        ArrayList<URI> sources = new ArrayList<>();
        sources.add(currentPositionRequestUri);
        if (!stations.isEmpty()) {
            sources.add(overviewRequestUri);
        }
        return new Voyage(carrier, time, currentStation, stations, sources, generatedTime);
    }

    private CloseableHttpResponse executeRequest(URI uri) {
        try {
            HttpGet request = new HttpGet(uri);
            return this.httpClient.execute(request);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Document parseDocument(CloseableHttpResponse response) {
        try {
            String html = EntityUtils.toString(response.getEntity(), "Cp1250");
            return Jsoup.parse(html);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static URI buildOverviewRequestUri(String train, LocalDate date) {
        try {
            // @formatter:off
            return new URIBuilder("http://najava.hzinfra.hr/hzinfo/default.asp")
                    .addParameter("vl", train)
                    .addParameter("d1", date.format(formatter))
                    .addParameter("category", "korisnici")
                    .addParameter("service", "pkvl")
                    .addParameter("screen", "2")
                    .build();
            // @formatter:on
        }
        catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private static URI buildCurrentPositionRequestUri(String train) {
        try {
            // @formatter:off
            return new URIBuilder("http://vred.hzinfra.hr/hzinfo/Default.asp")
                    .addParameter("vl", train)
                    .addParameter("category", "hzinfo")
                    .addParameter("service", "tpvl")
                    .addParameter("screen", "2")
                    .build();
            // @formatter:on
        }
        catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}
