package com.carbontrack.user_service.service;

import com.carbontrack.user_service.entity.User;
import com.carbontrack.user_service.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Data
public class UserService {

    private final UserRepository userRepository;
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public User getUserById(Long id) {
        log.info("Get user by ID: {}", id);
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByUsername(String username) {
        log.info("Get user by username: {}", username);
        return userRepository.findByUsername(username).orElse(null);
    }

    public User createUser(User user) {
        log.info("Creating user: {}", user.getUsername());
        return userRepository.save(user);
    }

    public User updateUser(Long id, User userDetails) {
        log.info("Updating user: {}", id);
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setUsername(userDetails.getUsername());
            user.setEmail(userDetails.getEmail());
            user.setRole(userDetails.getRole());
            return userRepository.save(user);
        }
        return null;
    }

    public void deleteUser(Long id) {
        log.info("Deleting user: {}", id);
        userRepository.deleteById(id);
    }

}
