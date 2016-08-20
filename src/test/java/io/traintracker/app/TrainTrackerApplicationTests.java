package io.traintracker.app;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TrainTrackerApplicationTests {

	@Autowired
	private ApplicationContext context;

	@Test
	public void contextLoads() {
		Assertions.assertThat(this.context.getBean(TrainTrackerApplication.class)).isNotNull();
	}

}
