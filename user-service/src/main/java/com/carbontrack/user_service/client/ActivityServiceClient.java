package com.carbontrack.user_service.client;

import com.carbontrack.user_service.dto.ActivityDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/** * Feign client to communicate with Activity Service * Allows user-service to fetch activities from activity-service (different database) */
@FeignClient(
        name = "activity-service",
        url = "${activity-service.url:http://localhost:8083}"
)
public interface ActivityServiceClient {

    @GetMapping("/api/activities/user/{userId}")
    List<ActivityDto> getActivities(
            @PathVariable("userId") Long userId
    );

}
