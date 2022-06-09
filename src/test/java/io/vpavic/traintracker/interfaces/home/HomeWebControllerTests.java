package io.vpavic.traintracker.interfaces.home;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.endsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HomeWebController.class)
class HomeWebControllerTests {

	@Autowired
	private MockMvc mvc;

	@Test
	void findVoyage_ShouldRedirect() throws Exception {
		// when
		ResultActions result = this.mvc.perform(get("/web"));
		// then
		result.andExpect(status().is3xxRedirection());
		result.andExpect(header().string(HttpHeaders.LOCATION, endsWith("/web/hzpp")));
	}

}
