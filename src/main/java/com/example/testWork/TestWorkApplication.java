package com.example.testWork;

import net.datafaker.Faker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
@EnableCaching
public class TestWorkApplication {

    public static void main(String[] args) {
    SpringApplication.run(TestWorkApplication.class, args);
        }
	@Bean
	public Faker getFaker() {
		return new Faker();
	}

}
