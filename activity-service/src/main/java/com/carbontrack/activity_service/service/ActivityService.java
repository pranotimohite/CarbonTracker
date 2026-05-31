package com.carbontrack.activity_service.service;

import com.carbontrack.activity_service.dto.ChartDataDTO;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.carbontrack.activity_service.entity.Activity;
import com.carbontrack.activity_service.entity.User;
import com.carbontrack.activity_service.repository.ActivityRepository;
import com.carbontrack.security.util.SecurityUtils;
import lombok.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        // If controller didn't provide a User object, try to set id from SecurityUtils
        if (activity.getUser() == null || activity.getUser().getId() == null) {
            Long userId = SecurityUtils.getUserId();
            if (userId != null) {
                User user = new User();
                user.setId(userId);
                activity.setUser(user);
            }
        }
        return this.repository.save(activity);
    }

    public List<Activity> getAllActivities() {
        return this.repository.findAll();
    }

    public List<Activity> getActivitiesByUserId(Long userId) {
        return this.repository.findByUserId(userId);
    }

    public Page<Activity> getUserActivities(Pageable pageable) {
        Long userId = SecurityUtils.getUserId();
        // use repository derived query for pageable by user id
        return repository.findByUserId(userId, pageable);
    }

    public Map<String, Double> getEmissionStats(Long userId) {
        // repository returns raw rows (type, sum) — convert to Map
        List<Object[]> rows = repository.getEmissionStatsRaw(userId);
        return rows.stream()
                .collect(Collectors.toMap(
                        r -> (String) r[0],
                        r -> ((Number) r[1]).doubleValue()
                ));
    }

    public List<ChartDataDTO> getChartData(Long userId) {

        return repository.getCO2ByType(userId);
    }

    @Generated
    public ActivityService() {
    }
}
