package tv.skimo.connector;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import tv.skimo.connector.lib.StorageProperties;
import tv.skimo.connector.services.StorageService;

@SpringBootApplication
@EnableScheduling
@ComponentScan({"tv.skimo.connector"})
@EnableConfigurationProperties(StorageProperties.class)
public class SkimoConnectorTemplateApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkimoConnectorTemplateApplication.class, args);
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.init();
		};
	}
}
 