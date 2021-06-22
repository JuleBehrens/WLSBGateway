package com.SE3.Gateway;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class GatewayApplicationTests {

	final static private String localhost = "http://localhost:";

	@LocalServerPort
	private int port;

	@Autowired
	private GatewayApplication application;

	@Autowired
	private TestRestTemplate restTemplate;


	/**
	 * Tests if application is started
	 */
	@Test
	public void contextLoads() {
		assertThat(application).isNotNull();
	}

	/**
	 * Tests if the answer of /api contains links
	 */
	@Test
	public void getApi() {
		String answerString = this.restTemplate.getForObject(localhost + port + "/",
		String.class);
		assertThat(answerString).contains("_links");
	}

	/**
	 * Tests a valid input for /api/schedule
	 */
	@ParameterizedTest
	@ValueSource(strings = {"?nap=true&age=22&breakfast=true&wakeUpTime=08:00&getReadyDuration=01:00&workingHours=07:00"}) // six numbers
	public void getSchedule(String params) {
		String url = localhost + port + "/api/schedule" + params;
		String answerString = this.restTemplate.getForObject(url, String.class);
		assertThat(answerString).withFailMessage("Data cannot be retrieved: Ensure that WLSBApplication is started on port 8080").contains("status", "\"1\"", "schedule");
	}

	@ParameterizedTest
	@ValueSource(strings = {"?number=1&tags=vegetarian,dessert",
							"?number=5&tags=pescetarian,snack,fingerfood",
							"?number=1&tags=vegan,spanish",
							"?number=1&tags=gluten-free,soup,african",
							"?number=1&tags=dessert,carribean",
							"?number=1&tags=main-course,vienamese"}) // x numbers; keine verschiedenen cuisines und keine verschiedenen meal-types
	public void getRecipe(String params) {
		String url = localhost + port + "/api/recipes" + params;
		String answerString = this.restTemplate.getForObject(url, String.class);
		assertThat(answerString).contains("recipes");
	}
	@ParameterizedTest
	@ValueSource(strings = {"?location=49.01079,8.40865&startTime=2021-06-14T19:09:50Z&endTime=2021-06-15T10:09:50Z&timesteps=1h&timezone=Europe/Berlin",
							"?location=53.5872222,9.89861111111111&startTime=2021-06-22T19:09:50Z&endTime=2021-06-23T10:09:50Z&timesteps=1h&timezone=Europe/Berlin",
							"?location=52.9984971,9.3806941&startTime=2021-06-30T10:00:00Z&endTime=2021-07-1T10:00:00Z&timesteps=2&timezone=Europe/Berlin",
							"?location=21.304547,-157.855676&startTime=2021-09-22T00:00:00Z&endTime=2021-09-30T00:00:00Z&timesteps=10&timezone=Pacific/Honolulu",
							"??location=55.7504461,37.6174943&startTime=2021-07-01T12:34:56Z&endTime=2021-07-02T12:34:56Z&timesteps=2&timezone=Europe/Moscow",
							"?location=-45.0321923,168.661&startTime=2021-07-10T12:00:00Z&endTime=2021-07-20T12:00:00Z&timesteps=24&timezone=Pacific/Auckland"}) // 5 numbers
	public void getWeather(String params) {
		String url = localhost + port + "/api/weather" + params;
		String answerString = this.restTemplate.getForObject(url, String.class);
		assertThat(answerString).contains("recipes");
	}
	/**
	 * ToDo: Test Methoden für Meditation,
	 */
		
}
