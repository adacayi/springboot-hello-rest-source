package com.sanver.trials.springboot_hello_rest_source.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanver.trials.springboot_hello_rest_source.models.GreetingDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.time.LocalTime;

import static com.sanver.trials.springboot_hello_rest_source.models.Helpers.getIp;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@WebMvcTest(GreetingController.class)
@ConfigurationProperties(prefix = "greeting")
public class GreetingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GreetingController controller;

    private String greetingMessage;
    private String greetingPostMessage;

    public void setGreetingMessage(String greetingMessage) {
        this.greetingMessage = greetingMessage;
    }

    public void setGreetingPostMessage(String greetingPostMessage) {
        this.greetingPostMessage = greetingPostMessage;
    }

    @Test
    public void should_LoadContext() {
        assertNotNull(controller);
    }

    @Test
    public void should_ReturnMessageAhmetAgeBirthDate() throws Exception {
        String name = "Ahmet";
        LocalDate birthDate = LocalDate.of(2016, 3, 10);
        int age = birthDate.until(LocalDate.now()).getYears();
        ResultActions perform = mockMvc.perform(get(String.format("/greeting/greet?name=%s&birthdate=%s",
                name, birthDate.toString().replaceAll("-", ""))));
        perform.andDo(print()).andExpect(status().isOk());
        String contentAsString = perform.andReturn().getResponse().getContentAsString();
        GreetingDTO actual = objectMapper.readValue(contentAsString, GreetingDTO.class);
        GreetingDTO expectedClass = new GreetingDTO(name, String.format(greetingMessage, name, age, birthDate),
                age, birthDate, actual.getTime(), getIp());
        String expectedJSON = objectMapper.writeValueAsString(expectedClass);
        perform.andExpect(content().json(expectedJSON));
    }

    @Test
    public void should_ReturnMessageWithAgain_WhenPost() throws Exception {
        String name = "Ahmet";
        LocalDate birthDate = LocalDate.of(2016, 3, 10);
        int age = birthDate.until(LocalDate.now()).getYears();
        GreetingDTO inputDTO = new GreetingDTO(name, String.format(greetingMessage, name, age, birthDate),
                age, birthDate, LocalTime.now(), getIp());
        ResultActions perform = mockMvc.perform(post("/greeting/greet").contentType("application/json").
                content(objectMapper.writeValueAsBytes(inputDTO)));
        perform.andDo(print()).andExpect(status().isOk());
        String contentAsString = perform.andReturn().getResponse().getContentAsString();
        GreetingDTO actual = objectMapper.readValue(contentAsString, GreetingDTO.class);
        GreetingDTO expectedClass = new GreetingDTO(name, String.format(greetingPostMessage, name, age, birthDate), age,
                birthDate, actual.getTime(), getIp());
        String expectedJSON = objectMapper.writeValueAsString(expectedClass);
        perform.andExpect(content().json(expectedJSON));
    }
}