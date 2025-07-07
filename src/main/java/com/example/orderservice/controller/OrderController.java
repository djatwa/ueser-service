package com.example.orderservice.controller;

import com.example.orderservice.entity.Order;
import com.example.orderservice.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService service;

    @PostMapping
    public ResponseEntity<Order> create(@RequestBody Order o) {
        logger.info("Creating order for user ID: {}", o.getUserId());
        return new ResponseEntity<>(service.create(o), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> get(@PathVariable Long id) {
        logger.info("Fetching order with ID:", id);
        return ResponseEntity.ok(service.get(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> update(@PathVariable Long id, @RequestBody Order o) {
        logger.info("Updating order with ID:", id);
        return ResponseEntity.ok(service.update(id, o));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info("Deletinh order with ID:", id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
