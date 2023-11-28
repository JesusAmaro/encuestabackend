package edu.amaro.encuestabackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import edu.amaro.encuestabackend.security.AppProperties;

@SpringBootApplication
public class EncuestabackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EncuestabackendApplication.class, args);
	}

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	SpringApplicationContext springApplicationContext(){
		return new SpringApplicationContext();
	}

	@Bean(name="AppProperties")
	AppProperties getAppProperties(){
		return new AppProperties();
	}

}
