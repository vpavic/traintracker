package io.vpavic.traintracker.domain.model.carrier;

import java.io.Serializable;
import java.net.URL;
import java.time.ZoneId;
import java.util.Objects;

public class Carrier implements Serializable {

	private final CarrierId id;

	private final URL website;

	private final URL logo;

	private final ZoneId timeZone;

	private Carrier(CarrierId id, URL website, URL logo, ZoneId timeZone) {
		Objects.requireNonNull(id, "id must not be null");
		Objects.requireNonNull(website, "website must not be null");
		Objects.requireNonNull(logo, "logo must not be null");
		Objects.requireNonNull(timeZone, "timeZone must not be null");
		this.id = id;
		this.website = website;
		this.logo = logo;
		this.timeZone = timeZone;
	}

	public static Carrier of(CarrierId id, URL website, URL logo, ZoneId timeZone) {
		return new Carrier(id, website, logo, timeZone);
	}

	public CarrierId getId() {
		return this.id;
	}

	public URL getWebsite() {
		return this.website;
	}

	public URL getLogo() {
		return this.logo;
	}

	public ZoneId getTimeZone() {
		return this.timeZone;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		Carrier that = (Carrier) obj;
		return this.id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id);
	}

	@Override
	public String toString() {
		return this.id.toString();
	}

}
