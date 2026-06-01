package com.carbontrack.user_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
    @SequenceGenerator(
            name = "users_seq",
            sequenceName = "users_seq",
            allocationSize = 1
    )
    private Long id;

    @Column(name = "auth_id", unique = true)
    private Long authId;   // maps to auth-service id

    private String username;

    private String email;

    private String password;

    private String role;

    // ❌ REMOVED: @OneToMany relationship with Activity
    // Activity service owns Activity table in separate database
    // To fetch user activities, use ActivityServiceClient

    public User() {}

    public User(String username) {
        this.username = username;
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}