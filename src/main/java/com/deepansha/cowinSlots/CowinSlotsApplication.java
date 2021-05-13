package com.deepansha.cowinSlots;

import com.deepansha.cowinSlots.configuration.AppConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
@EnableConfigurationProperties(value = AppConfiguration.class)
@ComponentScan(basePackages = "com.deepansha.cowinSlots")
@EnableJpaRepositories("com.deepansha.cowinSlots.repo")
public class CowinSlotsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CowinSlotsApplication.class, args);
	}

}
