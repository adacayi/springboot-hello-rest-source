package com.sanver.trials.springboot_hello_rest_source;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@ConfigurationProperties(prefix = "greeting")
public class GreetingControllerTest {

    private String saying;
    private String backendServiceHost;
    private int backendServicePort;

    public void setSaying(String saying) {
        this.saying = saying;
    }

    public void setBackendServiceHost(String backendServiceHost) {
        this.backendServiceHost = backendServiceHost;
    }

    public void setBackendServicePort(int backendServicePort) {
        this.backendServicePort = backendServicePort;
    }

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnNameAndGreeting() throws Exception {
        String name = "Ahmet";
        GreetingDTO expected = new GreetingDTO(
                String.format(saying, name) + " Message from " + backendServiceHost + ":" + backendServicePort,
                LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(), getIp());
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/backend/greeting?name=" + name));
        resultActions.andDo(print()).andExpect(status().isOk());
        String actualString = resultActions.andReturn().getResponse().getContentAsString();
        GreetingDTO actual = asObject(actualString, GreetingDTO.class);
        expected.setTime(actual.getTime());
        String expectedString = asJsonString(expected);
        resultActions.andExpect(MockMvcResultMatchers.content().json(expectedString));
    }

    private String getIp() {
        String hostname;
        try {
            hostname = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            hostname = "unknown";
        }
        return hostname;
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T asObject(String json, Class<T> value) {
        try {
            return new ObjectMapper().readValue(json, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}