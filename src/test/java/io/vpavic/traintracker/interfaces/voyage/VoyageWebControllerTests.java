package io.vpavic.traintracker.interfaces.voyage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import io.vpavic.traintracker.application.VoyageFetcher;
import io.vpavic.traintracker.domain.model.carrier.Carrier;
import io.vpavic.traintracker.domain.model.carrier.Carriers;
import io.vpavic.traintracker.domain.model.voyage.Station;
import io.vpavic.traintracker.domain.model.voyage.Voyage;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VoyageWebController.class)
class VoyageWebControllerTests {

	@Autowired
	private MockMvc mvc;

	@SpyBean
	private VoyageFetcher voyageFetcher;

	@Test
	void getVoyageFragment_UnknownCarrierId_ShouldReturnNotFound() throws Exception {
		// when
		ResultActions result = this.mvc.perform(get("/voyages")
				.param("carrier-id", "test")
				.param("train-no", "123"));
		// then
		result.andExpect(status().isNotFound());
		then(this.voyageFetcher).should(never()).getVoyage(anyString());
	}

	@Test
	void getVoyageFragment_ValidCarrierId_ShouldReturnOk() throws Exception {
		// when
		ResultActions result = this.mvc.perform(get("/voyages")
				.header("HX-Request", "true")
				.param("carrier-id", Carriers.hzpp.getId().toString())
				.param("train-no", "123"));
		// then
		result.andExpect(status().isOk());
		result.andExpectAll(
				header().string("HX-Push", "/hzpp/123"),
				content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
		then(this.voyageFetcher).should().getVoyage(eq("123"));
	}

	@Test
	void getVoyagePage_UnknownCarrierId_ShouldReturnNotFound() throws Exception {
		// when
		ResultActions result = this.mvc.perform(get("/test/123"));
		// then
		result.andExpect(status().isNotFound());
		then(this.voyageFetcher).should(never()).getVoyage(anyString());
	}

	@Test
	void getVoyagePage_ValidCarrierId_ShouldReturnOk() throws Exception {
		// when
		ResultActions result = this.mvc.perform(get("/hzpp/123"));
		// then
		result.andExpect(status().isOk());
		result.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
		then(this.voyageFetcher).should().getVoyage(eq("123"));
	}

	@TestConfiguration(proxyBeanMethods = false)
	static class Config {

		@Bean
		VoyageFetcher voyageFetcher() {
			return new VoyageFetcher() {

				@Override
				public Carrier getCarrier() {
					return Carriers.hzpp;
				}

				@Override
				public Voyage getVoyage(String train) {
					return new Voyage(Carriers.hzpp.getId(), LocalDate.EPOCH, new Station("Test"), List.of(), List.of(),
							LocalTime.NOON);
				}

			};
		}

	}

}
