package xyz.vpavic.traintracker.domain.model.carrier;

import java.io.Serializable;
import java.util.Objects;

public final class CarrierId implements Serializable {

	private final String value;

	private CarrierId(String value) {
		Objects.requireNonNull(value, "value must not be null");
		this.value = value;
	}

	public static CarrierId of(String value) {
		return new CarrierId(value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		CarrierId that = (CarrierId) obj;
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
