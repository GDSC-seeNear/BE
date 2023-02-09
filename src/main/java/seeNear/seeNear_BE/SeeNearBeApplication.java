package seeNear.seeNear_BE;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:/app.properties")
public class  SeeNearBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeeNearBeApplication.class, args);
	}

}
