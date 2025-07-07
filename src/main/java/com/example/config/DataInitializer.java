package com.example.config;

import com.example.orderservice.entity.Order;
import com.example.orderservice.repository.OrderRepository;
import com.example.userservice.entity.User;
import com.example.userservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);
    @Bean
    CommandLineRunner initData(UserRepository userRepository, OrderRepository orderRepository){
        return args -> {
            logger.info("Initializing user and orders");
            User user1 = new User(null,"dinesh", "secretKey123","dinesh@example.com");
            User user2 = new User(null,"sita", "sitaKey123","sita@example.com");
            user1 = userRepository.save(user1);
            user2 = userRepository.save(user2);
            logger.debug("Saved User: {}", user1.getUsername());

            Order order1 = new Order(null,user1.getId(), "laptop",1,999.99);
            Order order2 = new Order(null,user2.getId(), "Monitor",2,199.99);
            order1 = orderRepository.save(order1);
            order2 = orderRepository.save(order2);
            logger.debug("Saved order: {}", order1.getProduct());

        };
    }
}
