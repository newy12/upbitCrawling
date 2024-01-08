package com.craw.crawlingprogram;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaAuditing
@SpringBootApplication
@EnableScheduling
public class CrawlingProgramApplication {

    public static final String APPLICATION_LOCATIONS = "spring.config.location="
            + "optional:classpath:application-local.yml"
            + ", optional:/app/project/config/application.yml";
    public static void main(String[] args) {
        new SpringApplicationBuilder(CrawlingProgramApplication.class)
                .properties(APPLICATION_LOCATIONS)
                .run(args);
    }
}
