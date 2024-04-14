package com.ztpai.fishqi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@EntityScan(basePackages = "com.ztpai.fishqi.entity")
@CrossOrigin(origins = "http://localhost:5173")
public class FishqiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FishqiApplication.class, args);
	}

}
