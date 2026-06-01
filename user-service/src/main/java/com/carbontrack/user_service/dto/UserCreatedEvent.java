package com.carbontrack.user_service.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserCreatedEvent implements Serializable
{
    private Long id;         // auth-service user id
    private String username;
    private String role;

    public UserCreatedEvent() {}

    public UserCreatedEvent(Long id, String username, String role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }
}
