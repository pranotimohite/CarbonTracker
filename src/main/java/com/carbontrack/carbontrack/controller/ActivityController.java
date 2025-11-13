package com.carbontrack.carbontrack.controller;

import com.carbontrack.carbontrack.entity.Activity;
import com.carbontrack.carbontrack.service.ActivityService;
import java.util.List;
import java.util.Map;
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
    @Autowired
    private ActivityService service;

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
        return this.service.getEmissionStats();
    }

    @Generated
    public ActivityController() {
    }
}
