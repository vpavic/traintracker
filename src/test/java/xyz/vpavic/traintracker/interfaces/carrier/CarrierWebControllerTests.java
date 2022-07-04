package xyz.vpavic.traintracker.interfaces.carrier;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarrierWebController.class)
class CarrierWebControllerTests {

	@Autowired
	private MockMvc mvc;

	@Test
	void getCarrier_UnknownCarrierId_ShouldReturnNotFound() throws Exception {
		// when
		ResultActions result = this.mvc.perform(get("/web/test"));
		// then
		result.andExpect(status().isNotFound());
	}

	@Test
	void getCarrier_ValidCarrierId_ShouldReturnOk() throws Exception {
		// when
		ResultActions result = this.mvc.perform(get("/web/hzpp"));
		// then
		result.andExpect(status().isOk());
		result.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
	}

}
