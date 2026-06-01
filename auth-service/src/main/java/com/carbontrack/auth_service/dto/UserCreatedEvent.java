package com.carbontrack.auth_service.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
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
