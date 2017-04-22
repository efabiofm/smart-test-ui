package com.quenti.smarttestui.web.websocket.dto;

/**
 * DTO for storing a user's activity.
 */
public class TestDTO {

    private String name;

    public TestDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
