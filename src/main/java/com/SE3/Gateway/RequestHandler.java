package com.SE3.Gateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
* Class to handle API Request and redirect them to specific API
*/
@RestController
public class RequestHandler {
	/**
	 * Entry Point to API
	 * @return possible links to navigate to the functions of the APIs behind the Gateway
	 */
    @GetMapping("/api")
	public String api() {
		return "{\"_links\": {\"self\": \"/api\",\"schedule\": \"/api/schedule\"}}";
	}

	/**
	 * Handles Get-Requests to /api/schedule
	 * Determines a schedule for a typical Homeoffice day with the given parameters
	 * 
	 * @param nap boolean, true if a nap should be scheduled
	 * @param age int, age of the person of the 
	 * @param breakfast boolean, true if the person eats breakfast
	 * @param wakeUpTime String (Format: "HH:mm"), Time when the person plans to wake up
	 * @param getReadyDuration String (Format: "HH:mm"), Duration how long the persons needs for morning routine (Breakfast, hygiene, clothing etc.)
	 * @param workingHours String (Format: "HH:mm"), Duration how long the person has to work on an average day
	 * @return the schedule in JSON-Format and a status code (1: ok, 2: not enough sleep, 3: not possible to determine plan)
	 */
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