package io.vpavic.traintracker.domain.model.carrier;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

class CarrierTests {

    private static final CarrierId TEST_CARRIER_ID = CarrierId.of("test");

    private static final URL TEST_WEBSITE_URL = url("https://example.com/website");

    private static final URL TEST_LOGO_URL = url("https://example.com/logo");

    @Test
    void of_ValidArguments_ShouldCreateCarrier() {
        Carrier test = Carrier.of(TEST_CARRIER_ID, TEST_WEBSITE_URL, TEST_LOGO_URL, ZoneOffset.UTC);
        assertThat(test.getId()).isEqualTo(TEST_CARRIER_ID);
        assertThat(test.getWebsite()).isEqualTo(TEST_WEBSITE_URL);
        assertThat(test.getLogo()).isEqualTo(TEST_LOGO_URL);
        assertThat(test.getTimeZone()).isEqualTo(ZoneOffset.UTC);
    }

    @Test
    void of_NullId_ShouldThrowException() {
        assertThatNullPointerException()
                .isThrownBy(() -> Carrier.of(null, TEST_WEBSITE_URL, TEST_LOGO_URL, ZoneOffset.UTC))
                .withMessage("id must not be null");
    }

    @Test
    void of_NullWebsite_ShouldThrowException() {
        assertThatNullPointerException()
                .isThrownBy(() -> Carrier.of(TEST_CARRIER_ID, null, TEST_LOGO_URL, ZoneOffset.UTC))
                .withMessage("website must not be null");
    }

    @Test
    void of_NullLogo_ShouldThrowException() {
        assertThatNullPointerException()
                .isThrownBy(() -> Carrier.of(TEST_CARRIER_ID, TEST_WEBSITE_URL, null, ZoneOffset.UTC))
                .withMessage("logo must not be null");
    }

    @Test
    void of_NullTimeZone_ShouldThrowException() {
        assertThatNullPointerException()
                .isThrownBy(() -> Carrier.of(TEST_CARRIER_ID, TEST_WEBSITE_URL, TEST_LOGO_URL, null))
                .withMessage("timeZone must not be null");
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
