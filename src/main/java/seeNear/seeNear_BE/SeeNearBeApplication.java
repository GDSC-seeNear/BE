package seeNear.seeNear_BE;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.PropertySource;
import seeNear.seeNear_BE.domain.auth.AuthService;

@SpringBootApplication
@ServletComponentScan
@PropertySource("classpath:/app.properties")
public class  SeeNearBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeeNearBeApplication.class, args);
	}

}
