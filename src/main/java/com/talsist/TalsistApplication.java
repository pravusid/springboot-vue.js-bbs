package com.talsist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TalsistApplication {

    public static void main(String[] args) {
        SpringApplication.run(TalsistApplication.class, args);
    }

}
