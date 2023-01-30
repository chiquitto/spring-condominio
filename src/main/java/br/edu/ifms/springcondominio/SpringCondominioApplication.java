package br.edu.ifms.springcondominio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringCondominioApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCondominioApplication.class, args);
	}
	
	@GetMapping("/")
	public String index() {
		return "Ola mundo";
	}

}
