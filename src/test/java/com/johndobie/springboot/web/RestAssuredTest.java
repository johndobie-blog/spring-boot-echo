package com.johndobie.springboot.web;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static com.johndobie.springboot.web.controller.SpringController.ECHO_ENDPOINT;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestAssuredTest {

    private static final String MESSAGE = "Hello, World";

    @LocalServerPort
    private int port;

    protected RequestSpecification requestSpecification;

    @BeforeEach
    public void setup() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

        requestSpecification = new RequestSpecBuilder()
                                       .setPort(port)
                                       .build();
    }

    @Test
    public void greetingShouldReturnDefaultMessage() throws Exception {

        String message = given(requestSpecification)
                                 .param("message", MESSAGE)
                                 .when()
                                 .contentType("application/json")
                                 .get(ECHO_ENDPOINT)
                                 .then()
                                 .statusCode(200)
                                 .extract()
                                 .asString();

        assertThat(message).isEqualTo(MESSAGE);
    }
}
