package com.nurfad.jpaexercise;

import com.nurfad.jpaexercise.infrastructure.config.RsaKeyConfigProperties;
import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@Log
@SpringBootApplication
@EnableConfigurationProperties({RsaKeyConfigProperties.class})
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
