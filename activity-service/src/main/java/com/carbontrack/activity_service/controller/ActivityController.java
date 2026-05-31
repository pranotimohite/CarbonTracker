package com.carbontrack.activity_service.controller;

import com.carbontrack.carbontrack.dto.ChartDataDTO;
import com.carbontrack.carbontrack.entity.Activity;
import com.carbontrack.carbontrack.service.ActivityService;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    private SecurityUtils security;

    @Autowired
    public ActivityController(ActivityService service, SecurityUtils security) {
        this.service = service;
        this.security = security;
    }

    @PostMapping
    public Activity addActivity(@RequestBody Activity activity) {
        return this.service.saveActivity(activity);
    }

    @GetMapping
    public List<Activity> getAll() {
        return this.service.getAllActivities();
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
