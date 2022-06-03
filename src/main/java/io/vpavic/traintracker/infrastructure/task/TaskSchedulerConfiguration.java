package io.vpavic.traintracker.infrastructure.task;

import java.util.concurrent.TimeUnit;

import com.newrelic.api.agent.Trace;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import io.vpavic.traintracker.domain.model.carrier.Carriers;
import io.vpavic.traintracker.domain.model.voyage.VoyageId;
import io.vpavic.traintracker.domain.model.voyage.VoyageRepository;

@EnableScheduling
@Configuration(proxyBeanMethods = false)
class TaskSchedulerConfiguration {

	private final VoyageRepository voyageRepository;

	TaskSchedulerConfiguration(ObjectProvider<VoyageRepository> voyageRepository) {
		this.voyageRepository = voyageRepository.getObject();
	}

	@Trace(dispatcher = true)
	@Scheduled(fixedDelay = 60, timeUnit = TimeUnit.SECONDS)
	public void fetchVoyageHzpp211() {
		this.voyageRepository.findByCarrierIdAndVoyageId(Carriers.hzpp.getId(), VoyageId.of("211"));
	}

}
