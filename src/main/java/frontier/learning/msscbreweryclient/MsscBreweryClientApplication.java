package frontier.learning.msscbreweryclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
@EnableConfigurationProperties
public class MsscBreweryClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsscBreweryClientApplication.class, args);
	}

}
