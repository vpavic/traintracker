package io.github.vpavic.traintracker.domain.model.carrier;

import java.io.Serializable;
import java.time.ZoneId;
import java.util.Objects;

public class Carrier implements Serializable {

    private final String id;

    private final String name;

    private final String website;

    private final ZoneId timezone;

    public Carrier(String id, String name, String website, ZoneId timezone) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
        this.website = Objects.requireNonNull(website);
        this.timezone = Objects.requireNonNull(timezone);
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getWebsite() {
        return this.website;
    }

    public ZoneId getTimezone() {
        return this.timezone;
    }

}
