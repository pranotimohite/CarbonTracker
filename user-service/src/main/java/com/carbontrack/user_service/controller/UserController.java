package com.carbontrack.user_service.controller;

import com.carbontrack.user_service.client.ActivityServiceClient;
import com.carbontrack.user_service.entity.User;
//import com.carbontrack.carbontrack.util.JwtUtil;
import com.carbontrack.user_service.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Protected endpoints that rely on the SecurityContext populated by JwtRequestFilter.
 * This controller is outside /api/auth so the JWT filter will run and populate SecurityContext.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final ActivityServiceClient activityServiceClient;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService, ActivityServiceClient activityServiceClient) {
        this.userService = userService;
        this.activityServiceClient = activityServiceClient;
    }


    @GetMapping("/status/{id}")
    public ResponseEntity<Map<String, Object>> status(@PathVariable Long id) {
        User logged = userService.getUserById(id);

        if (logged == null) {
            // Not authenticated — return 401 so UI knows user must re-authenticate
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("authenticated", false));
        }

        return ResponseEntity.ok(Map.of(
                "authenticated", true,
                "username", logged.getUsername(),
                "userId", logged.getId()
        ));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Retrieve user profile by user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found",
                    content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        log.info("Fetching user with ID: {}", id);
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}/activities")
    @Operation(
            summary = "Get user's activities",
            description = "Retrieve all activities for a user from activity-service (cross-service call)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Activities retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "503", description = "Activity service unavailable (fallback: empty list)")
    })
    public ResponseEntity<?> getUserActivities(@PathVariable Long id) {
        log.info("Fetching activities for user: {}", id);
        try {
            List<?> activities = null;
//            List<?> activities = activityServiceClient.getActivitiesByUserId(id);
            return ResponseEntity.ok(activities);
        } catch (Exception e) {
            log.error("Error fetching activities for user {}: {}", id, e.getMessage());
            // Return HTTP 200 with empty list (graceful degradation)
            return ResponseEntity.ok(List.of());
        }
    }

    @PostMapping
    @Operation(summary = "Create new user", description = "Register a new user in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User created successfully",
                    content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    public ResponseEntity<User> createUser(@RequestBody User user) {
        log.info("Creating new user: {}", user.getUsername());
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping("/username/{username}")
    @Operation(summary = "Get user by username")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        log.info("Fetching user with username: {}", username);
        User user = userService.getUserByUsername(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }
}