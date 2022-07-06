package xyz.vpavic.traintracker.interfaces.carrier;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for {@link CarrierWebController}.
 */
@DisplayName("CarrierWebController")
@WebMvcTest(CarrierWebController.class)
class CarrierWebControllerTests {

	@Autowired
	private MockMvc mockMvc;

	private MockMvc mockMvc() {
		return this.mockMvc;
	}

	@Nested
	@DisplayName("when GET /web/{carrierId}")
	class WhenGetCarrier {

		@Test
		@DisplayName("given unknown carrier id then returns not found")
		void givenUnknownCarrierIdThenReturnsNotFound() throws Exception {
			// when
			ResultActions result = mockMvc().perform(get("/web/test"));
			// then
			result.andExpect(status().isNotFound());
		}

		@Test
		@DisplayName("given valid carrier id then returns OK")
		void givenValidCarrierIdThenReturnsOk() throws Exception {
			// when
			ResultActions result = mockMvc().perform(get("/web/hzpp"));
			// then
			result.andExpect(status().isOk());
			result.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
		}

	}

}
