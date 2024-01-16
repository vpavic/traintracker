package net.vpavic.traintracker.domain.model.agency;

import java.net.URI;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Objects;

public class Agency {

	private final AgencyId id;

	private final String name;

	private final URI url;

	private final ZoneId timezone;

	private final Locale language;

	private final String phone;

	private final URI fareUrl;

	private final String email;

	private Agency(Builder builder) {
		Objects.requireNonNull(builder.id, "id must not be null");
		Objects.requireNonNull(builder.name, "name must not be null");
		Objects.requireNonNull(builder.url, "url must not be null");
		Objects.requireNonNull(builder.timezone, "timezone must not be null");
		this.id = builder.id;
		this.name = builder.name;
		this.url = builder.url;
		this.timezone = builder.timezone;
		this.language = builder.language;
		this.phone = builder.phone;
		this.fareUrl = builder.fareUrl;
		this.email = builder.email;
	}

	public AgencyId getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public URI getUrl() {
		return this.url;
	}

	public ZoneId getTimezone() {
		return this.timezone;
	}

	public Locale getLanguage() {
		return this.language;
	}

	public String getPhone() {
		return this.phone;
	}

	public URI getFareUrl() {
		return this.fareUrl;
	}

	public String getEmail() {
		return this.email;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		Agency that = (Agency) obj;
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

	public static Agency.Builder builder(AgencyId id, String name, URI website, ZoneId timezone) {
		return new Builder(id, name, website, timezone);
	}

	public static class Builder {

		private final AgencyId id;

		private final String name;

		private final URI url;

		private final ZoneId timezone;

		private Locale language;

		private String phone;

		private URI fareUrl;

		private String email;

		private Builder(AgencyId id, String name, URI url, ZoneId timezone) {
			this.id = id;
			this.name = name;
			this.url = url;
			this.timezone = timezone;
		}

		public Builder language(Locale language) {
			this.language = language;
			return this;
		}

		public Builder phone(String phone) {
			this.phone = phone;
			return this;
		}

		public Builder fareUrl(URI fareUrl) {
			this.fareUrl = fareUrl;
			return this;
		}

		public Builder email(String email) {
			this.email = email;
			return this;
		}

		public Agency build() {
			return new Agency(this);
		}

	}

}
