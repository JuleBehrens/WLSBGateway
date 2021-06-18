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
		assertThat(answerString).contains("status", "\"1\"", "schedule");
	}

	/**
	 * ToDo: Test Methoden für Meditation, Wetter, Rezepte
	 */
}
