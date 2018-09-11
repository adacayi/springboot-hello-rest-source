package com.sanver.trials.springboot_hello_rest_source;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanver.trials.springboot_hello_rest_source.models.GreetingDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.time.LocalTime;

import static com.sanver.trials.springboot_hello_rest_source.models.Helpers.getIp;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@ConfigurationProperties(prefix = "greeting")
public class GreetingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String greetingMessage;

    public void setGreetingMessage(String greetingMessage) {
        this.greetingMessage = greetingMessage;
    }

    @Test
    public void should_ReturnAbdullah35BirthDate() throws Exception {
        String name = "Abdullah";
        int age = 35;
        LocalDate birthDate = LocalDate.of(1983, 9, 10);
        GreetingDTO expectedClass = new GreetingDTO(name, String.format(greetingMessage, name, age, birthDate), age, birthDate, LocalTime.now(), getIp());
        ResultActions perform = mockMvc.perform(get("/greeting/greet?name=Abdullah&age=35&birthdate=19830910"));
        perform.andDo(print()).andExpect(status().isOk());
        String contentAsString = perform.andReturn().getResponse().getContentAsString();
        GreetingDTO actual = objectMapper.readValue(contentAsString, GreetingDTO.class);
        expectedClass.setTime(actual.getTime());
        String expectedJSON = objectMapper.writeValueAsString(expectedClass);
        perform.andExpect(content().json(expectedJSON));
    }
}