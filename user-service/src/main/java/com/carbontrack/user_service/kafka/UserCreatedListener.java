package com.carbontrack.user_service.kafka;

import com.carbontrack.user_service.dto.UserCreatedEvent;
import com.carbontrack.user_service.entity.User;
import com.carbontrack.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserCreatedListener {

    private final UserRepository userRepository;

    @KafkaListener(topics = "user.created", groupId = "user-service-group")
    public void handleUserCreated(UserCreatedEvent event) {
        log.info("Received UserCreatedEvent: {}", event);
        try {
            // idempotency: ignore if already exists
            if (event.getId() == null) {
                log.warn("Received UserCreatedEvent with null id - ignoring");
                return;
            }
            if (userRepository.existsByAuthId(event.getId())) {
                log.info("User with authId {} already exists - ignoring", event.getId());
                return;
            }

            User user = new User();
            user.setAuthId(event.getId());
            user.setUsername(event.getUsername());
            user.setRole(event.getRole());
            // other default values as needed
            userRepository.save(user);
            log.info("Created user in user-service for authId {}", event.getId());
        } catch (Exception ex) {
            log.error("Error processing UserCreatedEvent: {}", ex.getMessage(), ex);
            // exception handling: sending to DLQ, retry etc. can be implemented
        }
    }

}
