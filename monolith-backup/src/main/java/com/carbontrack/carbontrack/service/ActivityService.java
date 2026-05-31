package com.carbontrack.carbontrack.service;

import com.carbontrack.carbontrack.dto.ChartDataDTO;
import com.carbontrack.carbontrack.entity.Activity;
import com.carbontrack.carbontrack.entity.User;
import com.carbontrack.carbontrack.repository.ActivityRepository;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.carbontrack.carbontrack.util.JwtUtil.getLoggedInUser;

@Service
public class ActivityService {
    @Autowired
    private ActivityRepository repository;
    @Autowired
    private CO2CalculationService co2Service;

    public Activity saveActivity(Activity activity) {
        double co2 = this.co2Service.calculateCO2(activity.getType(), activity.getValue());
        activity.setCo2Emitted(co2);
        User user = getLoggedInUser(); // from Spring Security
        activity.setUser(user);
        return this.repository.save(activity);
    }

    public List<Activity> getAllActivities() {
        return this.repository.findAll();
    }

    public Page<Activity> getUserActivities(Pageable pageable) {
        return repository.findByUser(getLoggedInUser(), pageable);
    }

    public Map<String, Double> getEmissionStats(Long userId) {
        User user = getLoggedInUser();
        return this.repository.getEmissionStats(user.getId());
    }

    public List<ChartDataDTO> getChartData(Long userId) {

        return repository.getCO2ByType(userId);
    }

    @Generated
    public ActivityService() {
    }
}
