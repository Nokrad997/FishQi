package com.ztpai.fishqi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.ztpai.fishqi.entity")
public class FishqiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FishqiApplication.class, args);
	}

}
