package seeNear.seeNear_BE;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
@ServletComponentScan
@PropertySource("classpath:/app.properties")
public class  SeeNearBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeeNearBeApplication.class, args);
	}


}
