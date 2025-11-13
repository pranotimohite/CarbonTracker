package com.carbontrack.carbontrack.service;

import com.carbontrack.carbontrack.entity.Activity;
import com.carbontrack.carbontrack.repository.ActivityRepository;
import com.carbontrack.carbontrack.service.CO2CalculationService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityService {
    @Autowired
    private ActivityRepository repository;
    @Autowired
    private CO2CalculationService co2Service;

    public Activity saveActivity(Activity activity) {
        double co2 = this.co2Service.calculateCO2(activity.getType(), activity.getValue());
        activity.setCo2Emitted(co2);
        return (Activity)this.repository.save(activity);
    }

    public List<Activity> getAllActivities() {
        return this.repository.findAll();
    }

    public Map<String, Double> getEmissionStats() {
        return this.repository.findAll().stream().collect(Collectors.groupingBy(Activity::getType, Collectors.summingDouble(Activity::getCo2Emitted)));
    }

    @Generated
    public ActivityService() {
    }
}
