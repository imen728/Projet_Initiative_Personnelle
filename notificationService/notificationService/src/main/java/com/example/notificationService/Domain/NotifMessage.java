package com.example.notificationService.Domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NotifMessage {

    @JsonProperty("alert-message")
    private String message;

    public NotifMessage() {
    }

    public NotifMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
