package com.SE3.Gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@GetMapping("/api")
	public String api() {
		return "{\"_links\": {\"self\": \"/api\",\"schedule\": \"/api/schedule\"}}";
	}
	@GetMapping("/api/schedule")
	public String schedule(@RequestParam(value = "nap") boolean nap, @RequestParam(value = "age") String age) {
		String url = String.format("http://localhost:8081/api/schedule?nap=%s&age=%s",""+nap,age);
		return HTTPClient.getRequest(url);
	}
}
