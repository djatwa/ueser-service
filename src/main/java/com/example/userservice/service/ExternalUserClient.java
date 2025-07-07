package com.example.userservice.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class ExternalUserClient {
    private final RestTemplate restTemplate = new RestTemplate();

    public void validateUser(Long userId) {
        try {
            restTemplate.getForObject("http://localhost:8080/users/" + userId, String.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND)
                throw new RuntimeException("User not found for order");
        }
    }
}
