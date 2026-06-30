package com.midassim.portfolio_tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling 	 // Arka planda zamanlanmış görevleri çalıştırmaya izin verir

public class PortfolioTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PortfolioTrackerApplication.class, args);
	}

}
