package com.johndobie.springboot.web;

import com.johndobie.springboot.web.controller.SpringController;
import com.johndobie.springboot.web.model.EchoModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static com.johndobie.springboot.web.controller.SpringController.ECHO_ENDPOINT;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = SpringController.class)
public class MockMvcTest {

    private static final String MESSAGE = "Hello, World";

    @Autowired
    private ObjectMapper objectMapper;


    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getShouldReturnDefaultMessage() throws Exception {
        this.mockMvc
                .perform(get(ECHO_ENDPOINT)
                                 .param("message", MESSAGE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(MESSAGE)));
    }

    @Test
    public void postShouldReturnDefaultMessage() throws Exception {

        EchoModel echoModel = EchoModel
                                      .builder()
                                      .message(MESSAGE)
                                      .build();

        this.mockMvc
                .perform(post(ECHO_ENDPOINT)
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .content(getJsonObjectAsString(echoModel)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(MESSAGE)));
    }

    private String getJsonObjectAsString(Object o) {
        try {
            String json = objectMapper.writeValueAsString(o);
            return json;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

