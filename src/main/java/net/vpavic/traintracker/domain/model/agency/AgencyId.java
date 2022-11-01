package net.vpavic.traintracker.domain.model.agency;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

public final class AgencyId {

	private final String value;

	private AgencyId(String value) {
		this.value = value;
	}

	public static AgencyId of(String value) {
		if (!StringUtils.isAlphanumeric(value)) {
			throw new IllegalArgumentException("Invalid agency id");
		}
		return new AgencyId(value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		AgencyId that = (AgencyId) obj;
		return this.value.equals(that.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.value);
	}

	@Override
	public String toString() {
		return this.value;
	}

}
