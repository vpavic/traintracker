package xyz.vpavic.traintracker.infrastructure.fetcher;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import xyz.vpavic.traintracker.domain.model.agency.Agencies;
import xyz.vpavic.traintracker.domain.model.voyage.Station;
import xyz.vpavic.traintracker.domain.model.voyage.Voyage;
import xyz.vpavic.traintracker.domain.model.voyage.VoyageId;

import static org.assertj.core.api.BDDAssertions.catchNullPointerException;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDSoftAssertions.thenSoftly;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

/**
 * Tests for {@link DefaultVoyageRepository}.
 */
@DisplayName("Default VoyageRepository")
@ExtendWith(MockitoExtension.class)
class DefaultVoyageRepositoryTests {

	@Nested
	@DisplayName("when #findByAgencyIdAndVoyageId")
	class WhenFindByAgencyIdAndVoyageId {

		@Mock
		private VoyageFetcher voyageFetcher;

		private DefaultVoyageRepository voyageRepository;

		@Test
		@DisplayName("given null AgencyId then throws exception")
		void givenNullAgencyIdThenThrowsException() {
			// given
			this.voyageRepository = new DefaultVoyageRepository(Collections.emptyList());
			// when
			NullPointerException exception = catchNullPointerException(
					() -> this.voyageRepository.findByAgencyIdAndVoyageId(null, VoyageId.of("123")));
			// then
			then(exception).as("Exception").hasMessage("agencyId must not be null");
		}

		@Test
		@DisplayName("given null VoyageId then throws exception")
		void givenNullVoyageIdThenThrowsException() {
			// given
			this.voyageRepository = new DefaultVoyageRepository(Collections.emptyList());
			// when
			NullPointerException exception = catchNullPointerException(
					() -> this.voyageRepository.findByAgencyIdAndVoyageId(Agencies.hz.getId(), null));
			// then
			then(exception).as("Exception").hasMessage("voyageId must not be null");
		}

		@Test
		@DisplayName("given unknown AgencyId then returns empty")
		void givenUnknownAgencyIdThenReturnsEmpty() {
			// given
			this.voyageRepository = new DefaultVoyageRepository(Collections.emptyList());
			// when
			Optional<Voyage> result = this.voyageRepository.findByAgencyIdAndVoyageId(Agencies.hz.getId(),
					VoyageId.of("123"));
			// then
			then(result).as("Voyage").isEmpty();
		}

		@Test
		@DisplayName("given unknown VoyageId then returns empty")
		void givenUnknownVoyageIdThenReturnsEmpty() {
			// given
			VoyageId voyageId = VoyageId.of("123");
			given(this.voyageFetcher.getAgency()).willReturn(Agencies.hz);
			this.voyageRepository = new DefaultVoyageRepository(List.of(this.voyageFetcher));
			// when
			Optional<Voyage> result = this.voyageRepository.findByAgencyIdAndVoyageId(Agencies.hz.getId(), voyageId);
			// then
			then(result).as("Voyage").isEmpty();
			BDDMockito.then(this.voyageFetcher).should().getVoyage(eq(voyageId));
			BDDMockito.then(this.voyageFetcher).shouldHaveNoMoreInteractions();
		}

		@Test
		@DisplayName("given known VoyageId then returns Voyage")
		void givenKnownVoyageIdThenReturnsVoyage() {
			// given
			VoyageId voyageId = VoyageId.of("123");
			given(this.voyageFetcher.getAgency()).willReturn(Agencies.hz);
			given(this.voyageFetcher.getVoyage(voyageId)).willReturn(Optional.of(new Voyage(voyageId,
					List.of(new Station("Test")), OffsetDateTime.now())));
			this.voyageRepository = new DefaultVoyageRepository(List.of(this.voyageFetcher));
			// when
			Optional<Voyage> result = this.voyageRepository.findByAgencyIdAndVoyageId(Agencies.hz.getId(), voyageId);
			// then
			then(result).hasValueSatisfying(voyage -> thenSoftly(softly -> {
				softly.then(voyage.getId()).as("Voyage id").isEqualTo(voyageId);
				softly.then(voyage.getCurrentStation().getName()).as("Voyage current station name").isEqualTo("Test");
			}));
			BDDMockito.then(this.voyageFetcher).should().getVoyage(eq(voyageId));
			BDDMockito.then(this.voyageFetcher).shouldHaveNoMoreInteractions();
		}

	}

}
