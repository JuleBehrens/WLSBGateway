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

	/**
	 * Handles Get-Requests for weather data, for more information also see https://docs.tomorrow.io/reference/api-introduction and https://docs.tomorrow.io/reference/data-layers-core
	 * @param location Coordinates of the location in format 'latitude,longitude' (Example: 49.01079,8.40865)
	 * @param startTime First Timestamp for which data is retrieved in  ISO 8601 format (Example '2019-03-20T14:09:50Z')
	 * @param endTime Last Timestamp for which data is retrieved in  ISO 8601 format (Example '2019-03-20T14:09:50Z')
	 * @param timeSteps Intervals for which data is retrieved, for format see https://docs.tomorrow.io/reference/data-layers-overview
	 * @param timeZone TimeZone in which timestamps are returned, for format see https://docs.tomorrow.io/reference/api-formats
	 * @return Answer in JSON with weather data (Temperature, Apperent Temperatue, Weather Code, Precipitation Intensity, Precipitation Type, Wind Speed) from https://www.tomorrow.io/
	 */
	@GetMapping("/api/weather")
	public String weather(	@RequestParam(value = "location") String location,
							@RequestParam(value = "startTime") String startTime,
							@RequestParam(value = "endTime") String endTime,
							@RequestParam(value = "timesteps") String timeSteps,
							@RequestParam(value = "timezone") String timeZone) {
		String url = String.format("https://api.tomorrow.io/v4/timelines?apikey=dIdeyiuMKXGdmuPO5nOfrwmh3d1BFLbo&location=%s&fields=temperature,temperatureApparent,weatherCode,precipitationIntensity,precipitationType,windSpeed&startTime=%s&endTime=%s&timesteps=%s&timezone=%s", location, startTime, endTime, timeSteps, timeZone);
		return HTTPClient.getRequest(url);
	}

	/**
	 * Handles Get-Requests for random recipes (dependent on tags)
	 * @param number number of recipes returned
	 * @param tags can be diets, meal types, cuisines, or intolerances (Example: 'vegetarian, dessert', more information about tags: https://spoonacular.com/food-api/docs#Diets, https://spoonacular.com/food-api/docs#Meal-Types, https://spoonacular.com/food-api/docs#Cuisines, https://spoonacular.com/food-api/docs#Intolerances)
	 * @return Answer in JSON with random recipes from https://spoonacular.com/food-api
	 */
	@GetMapping("/api/recipes")
	public String recipes(	@RequestParam(value = "number") int number,
							@RequestParam(value = "tags") String tags) {
		String url = String.format("https://api.spoonacular.com/recipes/random?apiKey=1c4404267111445c87d3e689ee099317&number=%s&tags=%s", number, tags);
		return HTTPClient.getRequest(url);
	}
}