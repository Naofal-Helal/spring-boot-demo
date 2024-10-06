package world.array.springboot.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import world.array.springboot.aop.business.BusinessService1;

import javax.swing.*;

@SpringBootApplication
@ComponentScan()
public class MyAspectOrientedApplication implements CommandLineRunner {
    Logger logger = LoggerFactory.getLogger(getClass());
    BusinessService1 businessService1;

    public MyAspectOrientedApplication(BusinessService1 businessService1) {
        this.businessService1 = businessService1;
    }

    public static void main(String[] args) {
        SpringApplication.run(MyAspectOrientedApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        logger.debug("Max Value is: {}",
                businessService1.calculateMax()
        );
    }
}
