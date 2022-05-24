package io.vpavic.traintracker.infrastructure.fetcher;

import java.time.LocalDate;
import java.time.LocalTime;
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
				() -> this.voyageRepository.findByCarrierIdAndVoyageId(null, "123"), NullPointerException.class);
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
		Optional<Voyage> voyage = this.voyageRepository.findByCarrierIdAndVoyageId(Carriers.hzpp.getId(), "123");
		// then
		then(voyage).as("Voyage").isEmpty();
	}

	@Test
	void findByCarrierIdAndVoyageId_UnknownVoyageId_ShouldReturnEmpty() {
		// given
		given(this.voyageFetcher.getCarrier()).willReturn(Carriers.hzpp);
		this.voyageRepository = new DefaultVoyageRepository(List.of(this.voyageFetcher));
		// when
		Optional<Voyage> voyage = this.voyageRepository.findByCarrierIdAndVoyageId(Carriers.hzpp.getId(), "123");
		// then
		then(voyage).as("Voyage").isEmpty();
		BDDMockito.then(this.voyageFetcher).should().getVoyage(eq("123"));
		BDDMockito.then(this.voyageFetcher).shouldHaveNoMoreInteractions();
	}

	@Test
	void findByCarrierIdAndVoyageId_KnownVoyageId_ShouldReturnVoyage() {
		// given
		given(this.voyageFetcher.getCarrier()).willReturn(Carriers.hzpp);
		given(this.voyageFetcher.getVoyage("123")).willReturn(new Voyage(Carriers.hzpp.getId(), LocalDate.EPOCH,
				new Station("Test"), List.of(), List.of(), LocalTime.NOON));
		this.voyageRepository = new DefaultVoyageRepository(List.of(this.voyageFetcher));
		// when
		Optional<Voyage> voyage = this.voyageRepository.findByCarrierIdAndVoyageId(Carriers.hzpp.getId(), "123");
		// then
		then(voyage).as("Voyage").isNotEmpty();
		then(voyage).hasValueSatisfying(v -> thenSoftly(softly -> {
			softly.then(v.getCarrierId()).as("Voyage carrier id").isEqualTo(Carriers.hzpp.getId());
			softly.then(v.getCurrentStation().getName()).as("Voyage current station name").isEqualTo("Test");
		}));
		BDDMockito.then(this.voyageFetcher).should().getVoyage(eq("123"));
		BDDMockito.then(this.voyageFetcher).shouldHaveNoMoreInteractions();
	}

}