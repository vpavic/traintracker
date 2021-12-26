package io.vpavic.traintracker;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(properties = "spring.flyway.enabled=false")
class TrainTrackerApplicationTests {

    @MockBean
    @SuppressWarnings("unused")
    private DataSource dataSource;

    @Test
    void contextLoads() {
    }

}
