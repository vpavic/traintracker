package io.vpavic.traintracker.interfaces.voyage;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import io.vpavic.traintracker.domain.model.carrier.Carriers;
import io.vpavic.traintracker.domain.model.voyage.Station;
import io.vpavic.traintracker.domain.model.voyage.Voyage;
import io.vpavic.traintracker.domain.model.voyage.VoyageId;
import io.vpavic.traintracker.domain.model.voyage.VoyageRepository;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VoyageWebController.class)
class VoyageWebControllerTests {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private VoyageRepository voyageRepository;

	@Test
	void getVoyageFragment_UnknownCarrierId_ShouldReturnNotFound() throws Exception {
		// when
		ResultActions result = this.mvc.perform(get("/web/test/voyages")
				.param("voyage-id", "123"));
		// then
		result.andExpect(status().isNotFound());
		then(this.voyageRepository).shouldHaveNoInteractions();
	}

	@Test
	void getVoyageFragment_ValidCarrierId_ShouldReturnOk() throws Exception {
		// given
		VoyageId voyageId = VoyageId.of("123");
		given(this.voyageRepository.findByCarrierIdAndVoyageId(Carriers.hzpp.getId(), voyageId))
				.willReturn(Optional.of(new Voyage(voyageId, List.of(new Station("Test")), OffsetDateTime.now())));
		// when
		ResultActions result = this.mvc.perform(get("/web/hzpp/voyages")
				.header("HX-Request", "true")
				.param("voyage-id", "123"));
		// then
		result.andExpect(status().isOk());
		result.andExpectAll(
				header().string("HX-Push", "/web/hzpp/123"),
				content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
		then(this.voyageRepository).should().findByCarrierIdAndVoyageId(eq(Carriers.hzpp.getId()), eq(voyageId));
		then(this.voyageRepository).shouldHaveNoMoreInteractions();
	}

	@Test
	void getVoyagePage_UnknownCarrierId_ShouldReturnNotFound() throws Exception {
		// when
		ResultActions result = this.mvc.perform(get("/web/test/123"));
		// then
		result.andExpect(status().isNotFound());
		then(this.voyageRepository).shouldHaveNoInteractions();
	}

	@Test
	void getVoyagePage_ValidCarrierId_ShouldReturnOk() throws Exception {
		// given
		VoyageId voyageId = VoyageId.of("123");
		given(this.voyageRepository.findByCarrierIdAndVoyageId(Carriers.hzpp.getId(), voyageId))
				.willReturn(Optional.of(new Voyage(voyageId, List.of(new Station("Test")), OffsetDateTime.now())));
		// when
		ResultActions result = this.mvc.perform(get("/web/hzpp/123"));
		// then
		result.andExpect(status().isOk());
		result.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
		then(this.voyageRepository).should().findByCarrierIdAndVoyageId(eq(Carriers.hzpp.getId()), eq(voyageId));
		then(this.voyageRepository).shouldHaveNoMoreInteractions();
	}

}
