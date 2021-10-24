package io.vpavic.traintracker.domain.model.carrier;

import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class Carriers {

    public static final Carrier hzpp = Carrier.of(CarrierId.of("hzpp"), url("http://www.hzpp.hr"),
            url("https://prodaja.hzpp.hr/Content/img/HZPP_logo_color_small.png"), ZoneId.of("Europe/Zagreb"));

    private static final Map<CarrierId, Carrier> carriers = new HashMap<>();

    static {
        for (Field field : Carriers.class.getDeclaredFields()) {
            if (field.getType().equals(Carrier.class)) {
                try {
                    Carrier carrier = (Carrier) field.get(null);
                    carriers.put(carrier.getId(), carrier);
                }
                catch (IllegalAccessException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    private Carriers() {
    }

    public static Map<CarrierId, Carrier> getAll() {
        return carriers;
    }

    public static Optional<Carrier> getById(CarrierId id) {
        return Optional.ofNullable(carriers.get(id));
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
