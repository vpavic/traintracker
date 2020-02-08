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

package io.traintracker.domain.model.voyage;

import java.io.Serializable;
import java.time.LocalTime;

public class Station implements Serializable {

    private String name;

    private LocalTime arrivalTime;

    private Integer arrivalDelay;

    private LocalTime departureTime;

    private Integer departureDelay;

    public Station(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public LocalTime getArrivalTime() {
        return this.arrivalTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Integer getArrivalDelay() {
        return this.arrivalDelay;
    }

    public void setArrivalDelay(Integer arrivalDelay) {
        this.arrivalDelay = arrivalDelay;
    }

    public LocalTime getDepartureTime() {
        return this.departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public Integer getDepartureDelay() {
        return this.departureDelay;
    }

    public void setDepartureDelay(Integer departureDelay) {
        this.departureDelay = departureDelay;
    }

}
