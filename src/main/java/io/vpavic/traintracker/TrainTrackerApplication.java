package io.vpavic.traintracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(proxyBeanMethods = false)
public class TrainTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrainTrackerApplication.class, args);
    }

}
