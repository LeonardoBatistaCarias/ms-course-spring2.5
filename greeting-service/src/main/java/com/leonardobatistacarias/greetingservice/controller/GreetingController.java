package com.leonardobatistacarias.greetingservice.controller;

import com.leonardobatistacarias.greetingservice.configuration.GreetingConfiguration;
import com.leonardobatistacarias.greetingservice.model.Greeting;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {
    private static final String TEMPLATE = "%s, %s!";

    private final GreetingConfiguration greetingConfiguration;

    public GreetingController(GreetingConfiguration greetingConfiguration) {
        this.greetingConfiguration = greetingConfiguration;
    }

    @GetMapping(value = "/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "") String name) {
        if(name.isEmpty())
            name = greetingConfiguration.getDefaultValue();

        return new Greeting(1L, String.format(TEMPLATE, greetingConfiguration.getGreeting(), name));
    }
}
