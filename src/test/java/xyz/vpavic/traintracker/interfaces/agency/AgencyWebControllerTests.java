package xyz.vpavic.traintracker.interfaces.agency;

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
 * Tests for {@link AgencyWebController}.
 */
@DisplayName("AgencyWebController")
@WebMvcTest(AgencyWebController.class)
class AgencyWebControllerTests {

	@Autowired
	private MockMvc mockMvc;

	private MockMvc mockMvc() {
		return this.mockMvc;
	}

	@Nested
	@DisplayName("when GET /web/{agencyId}")
	class WhenGetAgency {

		@Test
		@DisplayName("given unknown agency id then returns not found")
		void givenUnknownAgencyIdThenReturnsNotFound() throws Exception {
			// when
			ResultActions result = mockMvc().perform(get("/web/test"));
			// then
			result.andExpect(status().isNotFound());
		}

		@Test
		@DisplayName("given valid agency id then returns OK")
		void givenValidAgencyIdThenReturnsOk() throws Exception {
			// when
			ResultActions result = mockMvc().perform(get("/web/hz"));
			// then
			result.andExpect(status().isOk());
			result.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
		}

	}

}
