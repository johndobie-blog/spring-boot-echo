package com.johndobie.springboot.web;

import com.johndobie.springboot.web.controller.SpringController;
import com.johndobie.springboot.web.exception.ValidationError;
import com.johndobie.springboot.web.exception.ValidationErrors;
import com.johndobie.springboot.web.model.EchoModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static com.johndobie.springboot.web.controller.SpringController.ECHO_ENDPOINT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = SpringController.class)
public class GlobalExceptionHandlerMockMvcTest {

    private static final String MESSAGE = "This is a message which is too long for validation";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void postShouldReturnDefaultMessage() throws Exception {

        EchoModel echoModel = EchoModel
                                      .builder()
                                      .message(MESSAGE)
                                      .build();

        ValidationErrors errors = getValidationErrors();

        this.mockMvc
                .perform(post(ECHO_ENDPOINT)
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .content(getJson(echoModel)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(ResponseBodyMatchers
                                   .responseBody()
                                   .containsObjectAsJson(errors, ValidationErrors.class));
    }

    private ValidationErrors getValidationErrors() {
        ValidationError error = ValidationError

                                        .builder()
                                        .code("Size")
                                        .detail("message size must be between 1 and 20")
                                        .source("echoModel/message")
                                        .build();

        return ValidationErrors
                .builder()
                .validationErrors(Collections.singletonList(error))
                .build();

    }

    private String getJson(Object o) {
        try {
            String json = objectMapper.writeValueAsString(o);
            return json;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

