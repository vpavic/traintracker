package io.vpavic.traintracker.interfaces.voyage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import io.vpavic.traintracker.application.VoyageFetcher;
import io.vpavic.traintracker.domain.model.carrier.Carriers;
import io.vpavic.traintracker.domain.model.voyage.Station;
import io.vpavic.traintracker.domain.model.voyage.Voyage;

import static org.hamcrest.Matchers.endsWith;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VoyageWebController.class)
class VoyageWebControllerTests {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private VoyageFetcher voyageFetcher;

	@Test
	void findVoyage_ShouldRedirect() throws Exception {
		// when
		ResultActions result = this.mvc.perform(get("/test/voyages?train-no=123"));
		// then
		result.andExpectAll(
				status().is3xxRedirection(),
				header().string(HttpHeaders.LOCATION, endsWith("test/123")));
	}

	@Test
	void getVoyage_UnknownCarrierId_ShouldReturnBadRequest() throws Exception {
		// when
		ResultActions result = this.mvc.perform(get("/test/123"));
		// then
		result.andExpect(status().isBadRequest());
	}

	@Test
	void getVoyage_ValidCarrierId_ShouldReturnOk() throws Exception {
		// given
		given(this.voyageFetcher.getVoyage("123")).willReturn(new Voyage(Carriers.hzpp.getId(), LocalDate.EPOCH,
				new Station("Test"), List.of(), List.of(), LocalTime.NOON));
		// when
		ResultActions result = this.mvc.perform(get("/hzpp/123"));
		// then
		result.andExpectAll(
				status().isOk(),
				content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
	}

	@TestConfiguration(proxyBeanMethods = false)
	static class Config {

		@Bean
		VoyageFetcher voyageFetcher() {
			VoyageFetcher voyageFetcher = mock(VoyageFetcher.class);
			given(voyageFetcher.getCarrier()).willReturn(Carriers.hzpp);
			return voyageFetcher;
		}

	}

}
