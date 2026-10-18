package org.example.knockin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@EnableFeignClients
@SpringBootApplication
public class KnockInApplication {

    public static void main(String[] args) {
        SpringApplication.run(KnockInApplication.class, args);
    }

}
