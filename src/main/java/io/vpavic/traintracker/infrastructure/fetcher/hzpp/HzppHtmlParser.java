package io.vpavic.traintracker.infrastructure.fetcher.hzpp;

import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import io.vpavic.traintracker.domain.model.voyage.Station;

final class HzppHtmlParser {

    private HzppHtmlParser() {
    }

    static Station parseCurrentPosition(String html) {
        Station station = null;
        Document doc = Jsoup.parse(html);
        Elements tables = doc.getElementsByTag("tbody");
        if (tables.size() == 2) {
            Elements rows = tables.get(1).children();
            String nameRaw = rows.get(1).child(0).child(1).text().trim();
            Element position = rows.get(2);
            String direction = position.child(0).child(0).text().trim();
            String timeRaw = position.child(0).child(1).text().trim();
            String delayRaw = rows.get(3).child(0).child(0).child(0).text().trim();
            String name = nameRaw.replace('+', ' ');
            LocalTime time = LocalTime.parse(timeRaw.substring(12, 17));
            int delay = delayRaw.startsWith("Kasni") ? Integer.parseInt(delayRaw.substring(6, delayRaw.indexOf(' ', 6)))
                    : 0;
            if (direction.equals("Odlazak")) {
                station = new Station(name);
                station.setDepartureTime(time);
                station.setDepartureDelay(delay);
            }
            else if (direction.equals("Dolazak") || direction.equals("Završio vožnju")) {
                station = new Station(name);
                station.setArrivalTime(time);
                station.setArrivalDelay(delay);
            }
        }
        return station;
    }

    static List<Station> parseOverview(String html) {
        LinkedList<Station> stations = new LinkedList<>();
        Document doc = Jsoup.parse(html);
        Elements tables = doc.getElementsByTag("tbody");
        if (tables.size() == 3) {
            Elements rows = tables.get(2).children();
            rows.remove(0);
            for (Element row : rows) {
                String name = row.child(0).text().trim();
                String direction = row.child(1).text().trim();
                String time = row.child(3).text().trim();
                String delay = row.child(4).text().trim();
                Station station;
                if (direction.equals("Dolazak")) {
                    station = new Station(name);
                    station.setArrivalTime(LocalTime.parse(time));
                    station.setArrivalDelay(delay.isEmpty() ? 0 : Integer.parseInt(delay));
                }
                else {
                    if (!stations.isEmpty() && stations.peekLast().getName().equals(name)) {
                        station = stations.removeLast();
                    }
                    else {
                        station = new Station(name);
                    }
                    station.setDepartureTime(LocalTime.parse(time));
                    station.setDepartureDelay(delay.isEmpty() ? 0 : Integer.parseInt(delay));
                }
                stations.add(station);
            }
        }
        return stations;
    }

}
