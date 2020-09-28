package com.johndobie.springboot.web.controller;

import com.johndobie.springboot.web.model.EchoModel;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class SpringController {

    public static final String ECHO_ENDPOINT = "/echo";

    @GetMapping(ECHO_ENDPOINT)
    public String echo(@RequestParam String message) {
        return message;
    }

    @PostMapping(ECHO_ENDPOINT)
    public String Mapping(@Valid @RequestBody EchoModel model) {

        return model.getMessage();
    }
}