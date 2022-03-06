package com.example.club_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ClubProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClubProjectApplication.class, args);
    }

}
