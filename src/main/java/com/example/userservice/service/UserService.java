package com.example.userservice.service;

import com.example.userservice.entity.User;
import com.example.userservice.exception.UserNotFoundException;
import com.example.userservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserRepository repo;

    public User create(User user) {
        logger.debug("Saving user: {}", user.getUsername());
        return repo.save(user);
    }

    public User get(Long id) {
        logger.debug("Looking for user ID: {}", id);
        return repo.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    public User update(Long id, User updated) {
        logger.debug("Updating  user ID: {}", id);
        User user = get(id);
        user.setUsername(updated.getUsername());
        user.setEmail(updated.getEmail());
        user.setPassword(updated.getPassword());
        return repo.save(user);
    }

    public void delete(Long id) {
        logger.debug("Deleting user ID: {}", id);
        repo.delete(get(id));
    }
}
