package io.vpavic.traintracker.infrastructure.fetcher.hzpp;

import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import io.vpavic.traintracker.domain.model.voyage.Station;

import static org.assertj.core.api.Assertions.assertThat;

class HzppHtmlParserTests {

    @Test
    void parseOverview_OkResponse_ShouldReturnAllStations() {
        List<Station> stations = HzppHtmlParser.parseOverview(HzppSampleResponses.overviewOk);
        assertThat(stations).hasSize(35);
        Station currentStation = stations.get(34);
        assertThat(currentStation.getName()).isEqualTo("VINKOVCI");
        assertThat(currentStation.getArrivalTime()).isEqualTo(LocalTime.of(21, 28));
        assertThat(currentStation.getArrivalDelay()).isEqualTo(21);
        assertThat(currentStation.getDepartureTime()).isNull();
        assertThat(currentStation.getDepartureDelay()).isNull();
    }

    @Test
    void parseOverview_NotFoundResponse_ShouldReturnNoStations() {
        List<Station> stations = HzppHtmlParser.parseOverview(HzppSampleResponses.overviewNotFound);
        assertThat(stations).isEmpty();
    }

    @Test
    void parseCurrentPosition_OkResponse_ShouldReturnStation() {
        Station station = HzppHtmlParser.parseCurrentPosition(HzppSampleResponses.currentPositionOk);
        assertThat(station).isNotNull();
        assertThat(station.getName()).isEqualTo("NOVA KAPELA BATRINA");
        assertThat(station.getArrivalTime()).isNull();
        assertThat(station.getArrivalDelay()).isNull();
        assertThat(station.getDepartureTime()).isEqualTo(LocalTime.of(5, 43));
        assertThat(station.getDepartureDelay()).isEqualTo(27);
    }

    @Test
    void parseCurrentPosition_NotFoundResponse_ShouldReturnNull() {
        Station station = HzppHtmlParser.parseCurrentPosition(HzppSampleResponses.currentPositionNotFound);
        assertThat(station).isNull();
    }

}
