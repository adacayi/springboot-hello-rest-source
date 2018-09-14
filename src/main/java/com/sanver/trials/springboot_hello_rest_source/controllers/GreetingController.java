package com.sanver.trials.springboot_hello_rest_source.controllers;

import com.sanver.trials.springboot_hello_rest_source.models.GreetingDTO;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static com.sanver.trials.springboot_hello_rest_source.models.Helpers.getIp;

@RestController
@RequestMapping("/greeting")
@ConfigurationProperties(prefix = "greeting")
public class GreetingController {
    private String greetingMessage;
    private String greetingPostMessage;

    public void setGreetingMessage(String greetingMessage) {
        this.greetingMessage = greetingMessage;
    }

    public void setGreetingPostMessage(String greetingPostMessage) {
        this.greetingPostMessage = greetingPostMessage;
    }

    @GetMapping("/greet")
    public GreetingDTO greet(@RequestParam("name") String name, @RequestParam("birthdate") String birthDateString) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate birthDate = LocalDate.parse(birthDateString, dateTimeFormatter);
        int age = birthDate.until(LocalDate.now()).getYears();
        return new GreetingDTO(name, String.format(greetingMessage, name, age, birthDate), age, birthDate, LocalTime.now(), getIp());
    }

    @PostMapping("/greet")
    public GreetingDTO greetWithPost(@RequestBody GreetingDTO greetingDTO) {
        LocalDate birthDate = greetingDTO.getBirthDate();
        int age = birthDate.until(LocalDate.now()).getYears();
        return new GreetingDTO(greetingDTO.getName(), String.format(greetingPostMessage, greetingDTO.getName(),
                greetingDTO.getAge(), greetingDTO.getBirthDate()),
                age, birthDate, LocalTime.now(), getIp());
    }
}
