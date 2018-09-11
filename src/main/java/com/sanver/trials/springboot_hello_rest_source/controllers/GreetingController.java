package com.sanver.trials.springboot_hello_rest_source.controllers;

import com.sanver.trials.springboot_hello_rest_source.models.GreetingDTO;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static com.sanver.trials.springboot_hello_rest_source.models.Helpers.getIp;

@RestController
@RequestMapping("/greeting")
@ConfigurationProperties(prefix = "greeting")
public class GreetingController {
	private String greetingMessage;

	public void setGreetingMessage(String greetingMessage) {
		this.greetingMessage = greetingMessage;
	}

	@RequestMapping("/greet")
	public GreetingDTO greet(@RequestParam("name") String name, @RequestParam("age") int age, @RequestParam("birthdate") String birthDateString) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		LocalDate birthDate = LocalDate.parse(birthDateString, dateTimeFormatter);
		return new GreetingDTO(name, String.format(greetingMessage, name, age, birthDate), age, birthDate, LocalTime.now(), getIp());
	}
}
