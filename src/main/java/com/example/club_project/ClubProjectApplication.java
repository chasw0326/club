package com.example.club_project;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ClubProjectApplication {

    public static final String APPLICATION_PROPERTIES = String.format("%s=%s,%s",
        "spring.config.location",
        "classpath:application.yml",
        "classpath:aws.yml"
    );

    public static void main(String[] args) {
        new SpringApplicationBuilder(ClubProjectApplication.class)
                .properties(APPLICATION_PROPERTIES)
                .run(args);
    }

}
