package com.johndobie.springboot.web;

import com.johndobie.springboot.web.model.EchoModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.net.URI;

import static com.johndobie.springboot.web.controller.SpringController.ECHO_ENDPOINT;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestTemplateTest {

    private static final String MESSAGE = "Hello, World";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void greetingShouldReturnDefaultMessage() throws Exception {

        String baseUrl = "http://localhost:" + port + ECHO_ENDPOINT;
        URI uri = new URI(baseUrl);

        EchoModel echoModel = EchoModel
                                      .builder()
                                      .message(MESSAGE)
                                      .build();

        String result = this.restTemplate.postForObject(uri, echoModel, String.class);
        assertThat(result).isEqualTo(MESSAGE);
    }
}
