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
	public String schedule(	@RequestParam(value = "nap") boolean nap, 
							@RequestParam(value = "age") int age,
							@RequestParam(value = "breakfast") boolean breakfast, 
							@RequestParam(value = "wakeUpTime") String wakeUpTime,
							@RequestParam(value = "getReadyDuration") String getReadyDuration,
							@RequestParam(value = "workingHours") String workingHours) {
		String url = String.format("http://localhost:8081/api/schedule?nap=%s&age=%s&breakfast=%s&wakeUpTime=%s&getReadyDuration=%s&workingHours=%s",""+nap,""+age,""+breakfast,wakeUpTime, getReadyDuration, workingHours);
		return HTTPClient.getRequest(url);
	}
}
