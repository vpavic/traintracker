package xyz.vpavic.traintracker.domain.model.agency;

import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class Agencies {

	public static final Agency hz = Agency.builder(AgencyId.of("hz"), "HŽ Putnicki prijevoz", url("http://www.hzpp.hr"),
			ZoneId.of("Europe/Zagreb")).build();

	private static final Map<AgencyId, Agency> agencies = new HashMap<>();

	static {
		for (Field field : Agencies.class.getDeclaredFields()) {
			if (field.getType().equals(Agency.class)) {
				try {
					Agency agency = (Agency) field.get(null);
					agencies.put(agency.getId(), agency);
				}
				catch (IllegalAccessException ex) {
					throw new RuntimeException(ex);
				}
			}
		}
	}

	private Agencies() {
	}

	public static Map<AgencyId, Agency> getAll() {
		return agencies;
	}

	public static Optional<Agency> getById(AgencyId id) {
		return Optional.ofNullable(agencies.get(id));
	}

	private static URL url(String spec) {
		try {
			return new URL(spec);
		}
		catch (MalformedURLException ex) {
			throw new RuntimeException(ex);
		}
	}

}
