package xyz.vpavic.traintracker.interfaces.voyage;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import xyz.vpavic.traintracker.domain.model.carrier.Carriers;
import xyz.vpavic.traintracker.domain.model.voyage.Station;
import xyz.vpavic.traintracker.domain.model.voyage.Voyage;
import xyz.vpavic.traintracker.domain.model.voyage.VoyageId;
import xyz.vpavic.traintracker.domain.model.voyage.VoyageRepository;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for {@link VoyageWebController}.
 */
@DisplayName("VoyageWebController")
@WebMvcTest(VoyageWebController.class)
class VoyageWebControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private VoyageRepository voyageRepository;

	private MockMvc mockMvc() {
		return this.mockMvc;
	}

	private VoyageRepository voyageRepository() {
		return this.voyageRepository;
	}

	@Nested
	@DisplayName("when GET /web/{carrierId}/voyages")
	class WhenGetVoyageFragment {

		@Test
		@DisplayName("given unknown carrier id then returns not found")
		void givenUnknownCarrierIdThenReturnsNotFound() throws Exception {
			// when
			ResultActions result = mockMvc().perform(get("/web/test/voyages").param("voyage-id", "123"));
			// then
			result.andExpect(status().isNotFound());
			then(voyageRepository()).shouldHaveNoInteractions();
		}

		@Test
		@DisplayName("given valid carrier id then returns OK")
		void givenValidCarrierIdThenReturnsOk() throws Exception {
			// given
			VoyageId voyageId = VoyageId.of("123");
			given(voyageRepository().findByCarrierIdAndVoyageId(Carriers.hzpp.getId(), voyageId))
					.willReturn(Optional.of(new Voyage(voyageId, List.of(new Station("Test")), OffsetDateTime.now())));
			// when
			ResultActions result = mockMvc().perform(get("/web/hzpp/voyages")
					.header("HX-Request", "true")
					.param("voyage-id", "123"));
			// then
			result.andExpect(status().isOk());
			result.andExpectAll(
					header().string("HX-Push", "/web/hzpp/123"),
					content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
			then(voyageRepository()).should().findByCarrierIdAndVoyageId(eq(Carriers.hzpp.getId()), eq(voyageId));
			then(voyageRepository()).shouldHaveNoMoreInteractions();
		}

	}

	@Nested
	@DisplayName("when GET /web/{carrierId}/{voyageId}")
	class WhenGetVoyagePage {

		@Test
		@DisplayName("given unknown carrier id then returns not found")
		void givenUnknownCarrierIdThenReturnsNotFound() throws Exception {
			// when
			ResultActions result = mockMvc().perform(get("/web/test/123"));
			// then
			result.andExpect(status().isNotFound());
			then(voyageRepository()).shouldHaveNoInteractions();
		}

		@Test
		@DisplayName("given valid carrier id then returns OK")
		void givenValidCarrierIdThenReturnsOk() throws Exception {
			// given
			VoyageId voyageId = VoyageId.of("123");
			given(voyageRepository().findByCarrierIdAndVoyageId(Carriers.hzpp.getId(), voyageId))
					.willReturn(Optional.of(new Voyage(voyageId, List.of(new Station("Test")), OffsetDateTime.now())));
			// when
			ResultActions result = mockMvc().perform(get("/web/hzpp/123"));
			// then
			result.andExpect(status().isOk());
			result.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
			then(voyageRepository()).should().findByCarrierIdAndVoyageId(eq(Carriers.hzpp.getId()), eq(voyageId));
			then(voyageRepository()).shouldHaveNoMoreInteractions();
		}

	}

}
