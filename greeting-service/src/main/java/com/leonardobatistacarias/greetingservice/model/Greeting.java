package com.leonardobatistacarias.greetingservice.model;

public class Greeting {

    private Long id;
    private String description;

    public Greeting(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
