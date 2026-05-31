package com.carbontrack.activity_service.controller;

import com.carbontrack.activity_service.dto.ChartDataDTO;
import com.carbontrack.activity_service.entity.Activity;
import com.carbontrack.activity_service.entity.User;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.carbontrack.activity_service.service.ActivityService;
import com.carbontrack.security.util.SecurityUtils;
import lombok.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/activities"})
@CrossOrigin(origins={"*"})
public class ActivityController {

    private ActivityService service;

    @Autowired
    public ActivityController(ActivityService service) {
        this.service = service;
    }

    /**     * Create activity for the currently logged in user.     */
    @PostMapping
    public Activity addActivity(@RequestBody Activity activity) {

        Long userId = SecurityUtils.getUserId();
        Objects.requireNonNull(userId, "Unauthorized: userId is null");

        // set a lightweight User reference with id to satisfy the Activity.user relation
        User u = new User();
        u.setId(userId);
        activity.setUser(u);

        return this.service.saveActivity(activity);
    }

    @GetMapping
    public List<Activity> getAll() {

        Long userId = SecurityUtils.getUserId();
        Objects.requireNonNull(userId, "Unauthorized: userId is null");
        return this.service.getActivitiesByUserId(userId);
    }

    @GetMapping(value={"/stats"})
    public Map<String, Double> getStats() {
        Long userId = Objects.requireNonNull(SecurityUtils.getUserId());
        return this.service.getEmissionStats(userId);
    }

    @GetMapping("/chart")
    public List<ChartDataDTO> getChartData() {
        Long userId = Objects.requireNonNull(SecurityUtils.getUserId()); // implement this
        return service.getChartData(userId);

    }

    @Generated
    public ActivityController() {
    }
}
