package com.carbontrack.activity_service.controller;

import com.carbontrack.activity_service.dto.ChartDataDTO;
import com.carbontrack.activity_service.entity.Activity;
import com.carbontrack.activity_service.entity.User;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.carbontrack.activity_service.service.ActivityService;
import com.carbontrack.security.util.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value={"/api/activities"})
@CrossOrigin(origins={"*"})
@Tag(name = "Activity Management", description = "APIs for managing user activities and CO2 emissions")
@SecurityRequirement(name = "Bearer Authentication")
public class ActivityController {

    private ActivityService service;

    @Autowired
    public ActivityController(ActivityService service) {
        this.service = service;
    }

    /**     * Create activity for the currently logged in user.     */
    @PostMapping
    @Operation(summary = "Create a new activity", description = "Log a new activity for the currently authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Activity created successfully",
                    content = @Content(schema = @Schema(implementation = Activity.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - invalid or missing token"),
            @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    public ResponseEntity<Activity> addActivity(@RequestBody Activity activity) {

        Long userId = SecurityUtils.getUserId();
        Objects.requireNonNull(userId, "Unauthorized: userId is null");

        // set a lightweight User reference with id to satisfy the Activity.user relation
        User u = new User();
        u.setId(userId);
        activity.setUser(u);

        return ResponseEntity.ok(this.service.saveActivity(activity));
    }

    @GetMapping
    @Operation(summary = "Get all activities", description = "Retrieve all activities for the logged-in user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Activities retrieved successfully",
                    content = @Content(schema = @Schema(implementation = Activity.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - invalid or missing token")
    })
    public ResponseEntity<List<Activity>> getAll() {

        Long userId = SecurityUtils.getUserId();
        Objects.requireNonNull(userId, "Unauthorized: userId is null");
        return ResponseEntity.ok(this.service.getActivitiesByUserId(userId));
    }

    @GetMapping(value={"/stats"})
    @Operation(summary = "Get emission statistics", description = "Retrieve CO2 emission stats grouped by activity type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stats retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<Map<String, Double>> getStats() {
        Long userId = Objects.requireNonNull(SecurityUtils.getUserId());
        return ResponseEntity.ok(this.service.getEmissionStats(userId));
    }

    @GetMapping("/chart")
    @Operation(summary = "Get chart data", description = "Retrieve CO2 data formatted for chart visualization")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Chart data retrieved successfully",
                    content = @Content(schema = @Schema(implementation = ChartDataDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<List<ChartDataDTO>> getChartData() {
        Long userId = Objects.requireNonNull(SecurityUtils.getUserId()); // implement this
        return ResponseEntity.ok(service.getChartData(userId));

    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get activities by user ID", description = "Fetch all activities for a specific user (called by user-service)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Activities retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<List<Activity>> getActivitiesByUserId(@PathVariable Long userId) {
        Long authenticatedUserId = SecurityUtils.getUserId();
        Objects.requireNonNull(authenticatedUserId, "Unauthorized: userId is null");

        // Users can only fetch their own activities
        if (!userId.equals(authenticatedUserId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(this.service.getActivitiesByUserId(userId));
    }

    @Generated
    public ActivityController() {
    }
}
