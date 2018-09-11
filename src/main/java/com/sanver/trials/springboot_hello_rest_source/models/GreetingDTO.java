package com.sanver.trials.springboot_hello_rest_source.models;

import java.time.LocalDate;
import java.time.LocalTime;

public class GreetingDTO {
    private String name;
    private String message;
    private int age;
    private LocalDate birthDate;
    private LocalTime time;

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public int getAge() {
        return age;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getIp() {
        return ip;
    }

    private String ip;

    public GreetingDTO(String name, String message, int age, LocalDate birthDate, LocalTime time, String ip) {
        this.name = name;
        this.message = message;
        this.age = age;
        this.birthDate = birthDate;
        this.time = time;
        this.ip = ip;
    }

    public GreetingDTO() {
    }
}
