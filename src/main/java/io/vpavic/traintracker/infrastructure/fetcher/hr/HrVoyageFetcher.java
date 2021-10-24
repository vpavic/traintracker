package io.vpavic.traintracker.infrastructure.fetcher.hr;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Objects;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import io.vpavic.traintracker.application.VoyageFetcher;
import io.vpavic.traintracker.domain.model.carrier.Carrier;
import io.vpavic.traintracker.domain.model.carrier.Carriers;
import io.vpavic.traintracker.domain.model.voyage.Station;
import io.vpavic.traintracker.domain.model.voyage.Voyage;

@Component
class HrVoyageFetcher implements VoyageFetcher {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuMMdd");

    private final HttpClient httpClient;

    HrVoyageFetcher(HttpClient httpClient) {
        Objects.requireNonNull(httpClient, "httpClient must not be null");
        this.httpClient = httpClient;
    }

    HrVoyageFetcher() {
        this(defaultHttpClient());
    }

    private static HttpClient defaultHttpClient() {
        return HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5L))
                .build();
    }

    @Override
    public Carrier getCarrier() {
        return Carriers.hzpp;
    }

    @Override
    @Cacheable("voyages-hr")
    public Voyage getVoyage(String train) {
        URI currentPositionRequestUri = buildCurrentPositionRequestUri(train);
        Document currentPositionDocument = executeRequest(currentPositionRequestUri);
        Station currentStation = HrDocumentParser.parseCurrentPosition(currentPositionDocument);
        if (currentStation == null) {
            return null;
        }
        URI overviewRequestUri = buildOverviewRequestUri(train, LocalDate.now(Carriers.hzpp.getTimeZone()));
        Document doc = executeRequest(overviewRequestUri);
        Deque<Station> stations = HrDocumentParser.parseOverview(doc);
        LocalDate time = LocalDate.now(Carriers.hzpp.getTimeZone());
        LocalTime generatedTime = LocalTime.now(Carriers.hzpp.getTimeZone());
        ArrayList<URI> sources = new ArrayList<>();
        sources.add(currentPositionRequestUri);
        if (!stations.isEmpty()) {
            sources.add(overviewRequestUri);
        }
        return new Voyage(Carriers.hzpp, time, currentStation, stations, sources, generatedTime);
    }

    private Document executeRequest(URI uri) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .build();
            HttpResponse<String> response = this.httpClient.send(request,
                    HttpResponse.BodyHandlers.ofString(Charset.forName("Cp1250")));
            return Jsoup.parse(response.body());
        }
        catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static URI buildOverviewRequestUri(String train, LocalDate date) {
        String overviewUriTemplate = "http://najava.hzinfra.hr/hzinfo/default.asp" +
                "?vl=%s" +
                "&d1=%s" +
                "&category=korisnici" +
                "&service=pkvl" +
                "&screen=2";
        return URI.create(String.format(overviewUriTemplate, train, date.format(formatter)));
    }

    private static URI buildCurrentPositionRequestUri(String train) {
        String currentPositionUriTemplate = "http://vred.hzinfra.hr/hzinfo/Default.asp" +
                "?vl=%s" +
                "&category=hzinfo" +
                "&service=tpvl" +
                "&screen=2";
        return URI.create(String.format(currentPositionUriTemplate, train));
    }

}
