package io.vpavic.traintracker.infrastructure.fetcher.hzpp;

import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import io.vpavic.traintracker.domain.model.voyage.Station;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDSoftAssertions.thenSoftly;

class HzppHtmlParserTests {

    @Test
    void parseOverview_OkResponse_ShouldReturnAllStations() {
        // when
        List<Station> stations = HzppHtmlParser.parseOverview(HzppSampleResponses.overviewOk);
        // then
        then(stations).as("All stations").hasSize(35);
        Station currentStation = stations.get(34);
        thenSoftly(softly -> {
            softly.then(currentStation.getName()).as("Current station name").isEqualTo("VINKOVCI");
            softly.then(currentStation.getArrivalTime()).as("Current station arrival time")
                    .isEqualTo(LocalTime.of(21, 28));
            softly.then(currentStation.getArrivalDelay()).as("Current station arrival delay").isEqualTo(21);
            softly.then(currentStation.getDepartureTime()).as("Current station departure time").isNull();
            softly.then(currentStation.getDepartureDelay()).as("Current station departure delay").isNull();
        });
    }

    @Test
    void parseOverview_NotFoundResponse_ShouldReturnNoStations() {
        // when
        List<Station> stations = HzppHtmlParser.parseOverview(HzppSampleResponses.overviewNotFound);
        // then
        then(stations).as("All stations").isEmpty();
    }

    @Test
    void parseCurrentPosition_OkResponse_ShouldReturnStation() {
        // when
        Station station = HzppHtmlParser.parseCurrentPosition(HzppSampleResponses.currentPositionOk);
        // then
        then(station).as("Station").isNotNull();
        thenSoftly(softly -> {
            softly.then(station.getName()).isEqualTo("NOVA KAPELA BATRINA");
            softly.then(station.getArrivalTime()).isNull();
            softly.then(station.getArrivalDelay()).isNull();
            softly.then(station.getDepartureTime()).isEqualTo(LocalTime.of(5, 43));
            softly.then(station.getDepartureDelay()).isEqualTo(27);
        });
    }

    @Test
    void parseCurrentPosition_NotFoundResponse_ShouldReturnNull() {
        // when
        Station station = HzppHtmlParser.parseCurrentPosition(HzppSampleResponses.currentPositionNotFound);
        // then
        then(station).as("Station").isNull();
    }

}
