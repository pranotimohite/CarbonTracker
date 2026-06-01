package com.carbontrack.activity_service.repository;

import com.carbontrack.activity_service.dto.ChartDataDTO;
import com.carbontrack.activity_service.entity.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ActivityRepository
extends JpaRepository<Activity, Long> {

    @Query("SELECT a.type, SUM(a.co2Emitted) FROM Activity a WHERE a.user.id = :userId GROUP BY a.type")
    List<ChartDataDTO> getCO2ByType(Long userId);

    List<Activity> findByUserId(Long userId);
    Page<Activity> findByUserId(Long userId, Pageable pageable);

    // raw rows for stats conversion in service
    @Query("SELECT a.type, SUM(a.co2Emitted) FROM Activity a WHERE a.user.id = :userId GROUP BY a.type")
    List<Object[]> getEmissionStatsRaw(@Param("userId") Long userId);


    /*@Query("SELECT new map(a.type as type, SUM(a.co2Emitted) as total) " +
            "FROM Activity a WHERE a.user.id = :userId GROUP BY a.type")
    Map<String, Double> getEmissionStats(@Param("userId") Long userId);*/
}
