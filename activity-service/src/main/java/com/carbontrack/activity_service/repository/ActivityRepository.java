package com.carbontrack.carbontrack.repository;

import com.carbontrack.carbontrack.dto.ChartDataDTO;
import com.carbontrack.carbontrack.entity.Activity;
import com.carbontrack.carbontrack.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface ActivityRepository
extends JpaRepository<Activity, Long> {

    @Query("SELECT a.type, SUM(a.co2Emitted) FROM Activity a WHERE a.user.id = :userId GROUP BY a.type")
    List<ChartDataDTO> getCO2ByType(Long userId);

   /* // Recommended: Add to ActivityRepository
    @Query("SELECT a.type, SUM(a.co2Emitted) FROM Activity a GROUP BY a.type")
    Map<String, Double> getEmissionStats();*/

    Page<Activity> findByUser(User user, Pageable pageable);

    @Query("SELECT new map(a.type as type, SUM(a.co2Emitted) as total) " +
            "FROM Activity a WHERE a.user.id = :userId GROUP BY a.type")
    Map<String, Double> getEmissionStats(@Param("userId") Long userId);
}
