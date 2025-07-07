package com.example.orderservice.service;

import com.example.orderservice.entity.Order;
import com.example.orderservice.exception.OrderNotFoundException;
import com.example.orderservice.repository.OrderRepository;
import com.example.userservice.service.ExternalUserClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    @Autowired
    private OrderRepository repo;
    @Autowired
    private ExternalUserClient userClient;

    public Order create(Order order) {
        logger.debug("Saving new order for user ID: {}", order.getUserId());
        userClient.validateUser(order.getUserId());
        return repo.save(order);
    }

    public Order get(Long id) {
        logger.debug("SGeting order ID: {}",id);
        return repo.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
    }

    public Order update(Long id, Order updated) {
        logger.debug("Updating order ID: {}", id);
        Order o = get(id);
        o.setProduct(updated.getProduct());
        o.setQuantity(updated.getQuantity());
        o.setPrice(updated.getPrice());
        return repo.save(o);
    }

    public void delete(Long id) {
        logger.debug("Deleting order ID: {}", id);
        repo.delete(get(id));
    }
}
