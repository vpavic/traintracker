package io.traintracker.core;

import java.time.ZoneId;
import java.util.Objects;

public class Carrier {

	private String id;

	private String name;

	private String website;

	private ZoneId timezone;

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
