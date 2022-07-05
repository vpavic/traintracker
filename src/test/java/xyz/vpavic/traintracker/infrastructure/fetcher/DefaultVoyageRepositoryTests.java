package xyz.vpavic.traintracker.infrastructure.fetcher;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import xyz.vpavic.traintracker.domain.model.carrier.Carriers;
import xyz.vpavic.traintracker.domain.model.voyage.Station;
import xyz.vpavic.traintracker.domain.model.voyage.Voyage;
import xyz.vpavic.traintracker.domain.model.voyage.VoyageId;

import static org.assertj.core.api.BDDAssertions.catchNullPointerException;
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
		NullPointerException exception = catchNullPointerException(
				() -> this.voyageRepository.findByCarrierIdAndVoyageId(null, VoyageId.of("123")));
		// then
		then(exception).as("Exception").hasMessage("carrierId must not be null");
	}

	@Test
	void findByCarrierIdAndVoyageId_NullVoyageId_ShouldThrowException() {
		// given
		this.voyageRepository = new DefaultVoyageRepository(Collections.emptyList());
		// when
		NullPointerException exception = catchNullPointerException(
				() -> this.voyageRepository.findByCarrierIdAndVoyageId(Carriers.hzpp.getId(), null));
		// then
		then(exception).as("Exception").hasMessage("voyageId must not be null");
	}

	@Test
	void findByCarrierIdAndVoyageId_UnknownCarrierId_ShouldReturnEmpty() {
		// given
		this.voyageRepository = new DefaultVoyageRepository(Collections.emptyList());
		// when
		Optional<Voyage> result = this.voyageRepository.findByCarrierIdAndVoyageId(Carriers.hzpp.getId(),
				VoyageId.of("123"));
		// then
		then(result).as("Voyage").isEmpty();
	}

	@Test
	void findByCarrierIdAndVoyageId_UnknownVoyageId_ShouldReturnEmpty() {
		// given
		VoyageId voyageId = VoyageId.of("123");
		given(this.voyageFetcher.getCarrier()).willReturn(Carriers.hzpp);
		this.voyageRepository = new DefaultVoyageRepository(List.of(this.voyageFetcher));
		// when
		Optional<Voyage> result = this.voyageRepository.findByCarrierIdAndVoyageId(Carriers.hzpp.getId(), voyageId);
		// then
		then(result).as("Voyage").isEmpty();
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
		Optional<Voyage> result = this.voyageRepository.findByCarrierIdAndVoyageId(Carriers.hzpp.getId(), voyageId);
		// then
		then(result).hasValueSatisfying(voyage -> thenSoftly(softly -> {
			softly.then(voyage.getId()).as("Voyage id").isEqualTo(voyageId);
			softly.then(voyage.getCurrentStation().getName()).as("Voyage current station name").isEqualTo("Test");
		}));
		BDDMockito.then(this.voyageFetcher).should().getVoyage(eq(voyageId));
		BDDMockito.then(this.voyageFetcher).shouldHaveNoMoreInteractions();
	}

}