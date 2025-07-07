package com.example.userservice.controller;

import com.example.userservice.entity.User;
import com.example.userservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService service;

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User u) {
        logger.info("Received request to create user: {}", u.getUsername());
        return new ResponseEntity<>(service.create(u), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable Long id) {
        logger.info("Fetching user with id: {}", id);
        return ResponseEntity.ok(service.get(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User u) {
        logger.info("Update user with ID: {}", id);
        return ResponseEntity.ok(service.update(id, u));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info("Deleting user with id: {}", id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
