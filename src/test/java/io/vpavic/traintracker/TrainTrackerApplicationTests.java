package io.vpavic.traintracker;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@SpringBootTest(properties = "spring.flyway.enabled=false")
class TrainTrackerApplicationTests {

    @MockBean
    @SuppressWarnings("unused")
    private DataSource dataSource;

    @MockBean
    @SuppressWarnings("unused")
    private RedisConnectionFactory redisConnectionFactory;

    @Test
    void contextLoads() {
    }

}
