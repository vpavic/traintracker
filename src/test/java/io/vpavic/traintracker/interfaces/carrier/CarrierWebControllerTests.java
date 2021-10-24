package io.vpavic.traintracker.interfaces.carrier;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import io.vpavic.traintracker.config.SecurityConfiguration;

import static org.hamcrest.Matchers.endsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarrierWebController.class)
@Import(SecurityConfiguration.class)
class CarrierWebControllerTests {

    @Autowired
    private MockMvc mvc;

    @Test
    void getCarrier_NoTrailingSlash_ShouldReturnRedirect() throws Exception {
        this.mvc.perform(get("/test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string(HttpHeaders.LOCATION, endsWith("test/")));
    }

    @Test
    void getCarrier_UnknownCarrierId_ShouldReturnNotFound() throws Exception {
        this.mvc.perform(get("/test/"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getCarrier_ValidCarrierId_ShouldReturnOk() throws Exception {
        this.mvc.perform(get("/hzpp/"))
                .andExpect(status().isOk());
    }

}
