package com.carbontrack.carbontrack.repository;

import com.carbontrack.carbontrack.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository
extends JpaRepository<Activity, Long> {
}
