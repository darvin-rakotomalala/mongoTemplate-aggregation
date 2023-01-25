package com.poc;

import com.poc.common.logging.EnableLogModule;
import com.poc.common.logging.EnableLogThreadHandlingModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Slf4j
@EnableFeignClients
@EnableLogModule
@EnableLogThreadHandlingModule
@EnableWebMvc
@SpringBootApplication
public class MainApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("############################   RUN   ############################");
    }

}
