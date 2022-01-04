package io.vpavic.traintracker.interfaces.voyage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import io.vpavic.traintracker.application.VoyageFetcher;
import io.vpavic.traintracker.domain.model.carrier.Carriers;
import io.vpavic.traintracker.domain.model.voyage.Station;
import io.vpavic.traintracker.domain.model.voyage.Voyage;

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
	void getVoyageFragment_UnknownCarrierId_ShouldReturnBadRequest() throws Exception {
		// when
		ResultActions result = this.mvc.perform(get("/test/voyages?train-no=123"));
		// then
		result.andExpect(status().isBadRequest());
	}

	@Test
	void getVoyageFragment_ValidCarrierId_ShouldReturnOk() throws Exception {
		// given
		given(this.voyageFetcher.getVoyage("123")).willReturn(new Voyage(Carriers.hzpp.getId(), LocalDate.EPOCH,
				new Station("Test"), List.of(), List.of(), LocalTime.NOON));
		// when
		ResultActions result = this.mvc.perform(get("/hzpp/voyages?train-no=123").header("HX-Request", "true"));
		// then
		result.andExpect(status().isOk());
		result.andExpectAll(
				header().string("HX-Push", "/hzpp/123"),
				content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
	}

	@Test
	void getVoyagePage_UnknownCarrierId_ShouldReturnBadRequest() throws Exception {
		// when
		ResultActions result = this.mvc.perform(get("/test/123"));
		// then
		result.andExpect(status().isBadRequest());
	}

	@Test
	void getVoyagePage_ValidCarrierId_ShouldReturnOk() throws Exception {
		// given
		given(this.voyageFetcher.getVoyage("123")).willReturn(new Voyage(Carriers.hzpp.getId(), LocalDate.EPOCH,
				new Station("Test"), List.of(), List.of(), LocalTime.NOON));
		// when
		ResultActions result = this.mvc.perform(get("/hzpp/123"));
		// then
		result.andExpect(status().isOk());
		result.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
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
