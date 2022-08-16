package xyz.vpavic.traintracker.interfaces.home;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for {@link HomeWebController}.
 */
@DisplayName("HomeWebController")
@WebMvcTest(HomeWebController.class)
class HomeWebControllerTests {

	@Autowired
	private MockMvc mockMvc;

	private MockMvc mockMvc() {
		return this.mockMvc;
	}

	@Nested
	@DisplayName("when GET /web")
	class WhenGetHomePage {

		@Test
		@DisplayName("then redirects to /web/hz")
		void thenRedirectsToHz() throws Exception {
			// when
			ResultActions result = mockMvc().perform(get("/web"));
			// then
			result.andExpect(status().isFound());
			result.andExpect(redirectedUrl("/web/hz"));
		}

	}

}
