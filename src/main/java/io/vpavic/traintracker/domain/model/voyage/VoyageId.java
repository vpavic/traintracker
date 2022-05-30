package io.vpavic.traintracker.domain.model.voyage;

import java.io.Serializable;
import java.util.Objects;

public final class VoyageId implements Serializable {

	private final String value;

	private VoyageId(String value) {
		Objects.requireNonNull(value, "value must not be null");
		this.value = value;
	}

	public static VoyageId of(String value) {
		return new VoyageId(value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		VoyageId that = (VoyageId) obj;
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
