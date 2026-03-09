package com.CRUD;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StudentRegistryService {
    private static final Logger log = LoggerFactory.getLogger(StudentRegistryService.class);

    public static void main(String[] args) {
        SpringApplication.run(StudentRegistryService.class, args);
        log.info("Application Started!!!!");
    }

}
