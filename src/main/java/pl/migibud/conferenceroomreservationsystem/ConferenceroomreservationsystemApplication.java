package pl.migibud.conferenceroomreservationsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ConferenceroomreservationsystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConferenceroomreservationsystemApplication.class, args);
	}

}
