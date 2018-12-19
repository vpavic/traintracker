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

package io.traintracker.domain.model.voyage;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.Objects;

import io.traintracker.domain.model.carrier.Carrier;

public class Voyage implements Serializable {

    private Collection<Station> stations;

    private Station currentStation;

    private Carrier carrier;

    private String source;

    private LocalTime generatedTime;

    public Voyage(Station currentStation, Deque<Station> stations, Carrier carrier, String source) {
        Objects.requireNonNull(currentStation, "Current station must not be null");
        Objects.requireNonNull(stations, "Stations must not be null");
        Objects.requireNonNull(carrier, "Carrier must not be null");
        Objects.requireNonNull(source, "Source must not be null");
        this.currentStation = currentStation;
        this.stations = Collections.unmodifiableCollection(stations);
        this.carrier = carrier;
        this.source = source;
        this.generatedTime = LocalTime.now(carrier.getTimezone());
    }

    public Collection<Station> getStations() {
        return this.stations;
    }

    public Station getCurrentStation() {
        return this.currentStation;
    }

    public Carrier getCarrier() {
        return this.carrier;
    }

    public String getSource() {
        return this.source;
    }

    public LocalTime getGeneratedTime() {
        return this.generatedTime;
    }

}
