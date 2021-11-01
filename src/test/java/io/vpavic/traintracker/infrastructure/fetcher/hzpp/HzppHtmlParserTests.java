package io.vpavic.traintracker.infrastructure.fetcher.hzpp;

import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import io.vpavic.traintracker.domain.model.voyage.Station;

import static org.assertj.core.api.Assertions.assertThat;

class HzppHtmlParserTests {

    @Test
    void testPkvlOk() {
        String html = HzppHtmlHelper.getHtml("hr-pkvl-ok.html");
        List<Station> stations = HzppHtmlParser.parseOverview(html);
        assertThat(stations).hasSize(35);
        Station currentStation = stations.get(34);
        assertThat(currentStation.getName()).isEqualTo("VINKOVCI");
        assertThat(currentStation.getArrivalTime()).isEqualTo(LocalTime.of(21, 28));
        assertThat(currentStation.getArrivalDelay()).isEqualTo(21);
        assertThat(currentStation.getDepartureTime()).isNull();
        assertThat(currentStation.getDepartureDelay()).isNull();
    }

    @Test
    void testPkvlNotFound() {
        String html = HzppHtmlHelper.getHtml("hr-pkvl-not-found.html");
        List<Station> stations = HzppHtmlParser.parseOverview(html);
        assertThat(stations).isEmpty();
    }

    @Test
    void testTpvlOk() {
        String html = HzppHtmlHelper.getHtml("hr-tpvl-ok.html");
        Station station = HzppHtmlParser.parseCurrentPosition(html);
        assertThat(station).isNotNull();
        assertThat(station.getName()).isEqualTo("NOVA KAPELA BATRINA");
        assertThat(station.getArrivalTime()).isNull();
        assertThat(station.getArrivalDelay()).isNull();
        assertThat(station.getDepartureTime()).isEqualTo(LocalTime.of(5, 43));
        assertThat(station.getDepartureDelay()).isEqualTo(27);
    }

    @Test
    void testTpvlNotFound() {
        String html = HzppHtmlHelper.getHtml("hr-tpvl-not-found.html");
        Station station = HzppHtmlParser.parseCurrentPosition(html);
        assertThat(station).isNull();
    }

}
