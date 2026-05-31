package com.carbontrack.activity_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    private Long id;  // Primary key, matches user-service user.id
    private String username;
    private String role;
    // Don't store password, email, profile — those belong in user-service
}