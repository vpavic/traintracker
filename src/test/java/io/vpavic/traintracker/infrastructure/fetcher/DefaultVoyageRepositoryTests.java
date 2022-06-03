package io.vpavic.traintracker.infrastructure.fetcher;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.vpavic.traintracker.domain.model.carrier.Carriers;
import io.vpavic.traintracker.domain.model.voyage.Station;
import io.vpavic.traintracker.domain.model.voyage.Voyage;
import io.vpavic.traintracker.domain.model.voyage.VoyageId;

import static org.assertj.core.api.BDDAssertions.catchThrowableOfType;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDSoftAssertions.thenSoftly;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class DefaultVoyageRepositoryTests {

	@Mock
	private VoyageFetcher voyageFetcher;

	private DefaultVoyageRepository voyageRepository;

	@Test
	void findByCarrierIdAndVoyageId_NullCarrierId_ShouldThrowException() {
		// given
		this.voyageRepository = new DefaultVoyageRepository(Collections.emptyList());
		// when
		NullPointerException exception = catchThrowableOfType(
				() -> this.voyageRepository.findByCarrierIdAndVoyageId(null, VoyageId.of("123")),
				NullPointerException.class);
		// then
		then(exception).as("Exception").hasMessage("carrierId must not be null");
	}

	@Test
	void findByCarrierIdAndVoyageId_NullVoyageId_ShouldThrowException() {
		// given
		this.voyageRepository = new DefaultVoyageRepository(Collections.emptyList());
		// when
		NullPointerException exception = catchThrowableOfType(
				() -> this.voyageRepository.findByCarrierIdAndVoyageId(Carriers.hzpp.getId(), null),
				NullPointerException.class);
		// then
		then(exception).as("Exception").hasMessage("voyageId must not be null");
	}

	@Test
	void findByCarrierIdAndVoyageId_UnknownCarrierId_ShouldReturnEmpty() {
		// given
		this.voyageRepository = new DefaultVoyageRepository(Collections.emptyList());
		// when
		Optional<Voyage> voyage = this.voyageRepository.findByCarrierIdAndVoyageId(Carriers.hzpp.getId(),
				VoyageId.of("123"));
		// then
		then(voyage).as("Voyage").isEmpty();
	}

	@Test
	void findByCarrierIdAndVoyageId_UnknownVoyageId_ShouldReturnEmpty() {
		// given
		VoyageId voyageId = VoyageId.of("123");
		given(this.voyageFetcher.getCarrier()).willReturn(Carriers.hzpp);
		this.voyageRepository = new DefaultVoyageRepository(List.of(this.voyageFetcher));
		// when
		Optional<Voyage> voyage = this.voyageRepository.findByCarrierIdAndVoyageId(Carriers.hzpp.getId(), voyageId);
		// then
		then(voyage).as("Voyage").isEmpty();
		BDDMockito.then(this.voyageFetcher).should().getVoyage(eq(voyageId));
		BDDMockito.then(this.voyageFetcher).shouldHaveNoMoreInteractions();
	}

	@Test
	void findByCarrierIdAndVoyageId_KnownVoyageId_ShouldReturnVoyage() {
		// given
		VoyageId voyageId = VoyageId.of("123");
		given(this.voyageFetcher.getCarrier()).willReturn(Carriers.hzpp);
		given(this.voyageFetcher.getVoyage(voyageId)).willReturn(Optional.of(new Voyage(voyageId,
				List.of(new Station("Test")), OffsetDateTime.now())));
		this.voyageRepository = new DefaultVoyageRepository(List.of(this.voyageFetcher));
		// when
		Optional<Voyage> voyage = this.voyageRepository.findByCarrierIdAndVoyageId(Carriers.hzpp.getId(), voyageId);
		// then
		then(voyage).as("Voyage").isNotEmpty();
		then(voyage).hasValueSatisfying(v -> thenSoftly(softly -> {
			softly.then(v.getId()).as("Voyage id").isEqualTo(voyageId);
			softly.then(v.getCurrentStation().getName()).as("Voyage current station name").isEqualTo("Test");
		}));
		BDDMockito.then(this.voyageFetcher).should().getVoyage(eq(voyageId));
		BDDMockito.then(this.voyageFetcher).shouldHaveNoMoreInteractions();
	}

}
