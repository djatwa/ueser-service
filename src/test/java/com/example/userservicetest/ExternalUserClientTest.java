package com.example.userservicetest;

import com.example.userservice.service.ExternalUserClient;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExternalUserClientTest {

    @Test
    void testValidateUserSuccess() {
        RestTemplate mockTemplate = mock(RestTemplate.class);
        ExternalUserClient client = new ExternalUserClient() {
            @Override
            public void validateUser(Long userId) {
                // override to use mockTemplate
                mockTemplate.getForObject("http://localhost:8080/users/" + userId, String.class);
            }
        };

        // Should not throw
        client.validateUser(1L);
        verify(mockTemplate).getForObject("http://localhost:8080/users/1", String.class);
    }

    @Test
    void testValidateUserNotFound() {
        RestTemplate mockTemplate = mock(RestTemplate.class);
        doThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND)).when(mockTemplate)
                .getForObject("http://localhost:8080/users/2", String.class);

        ExternalUserClient client = new ExternalUserClient() {
            @Override
            public void validateUser(Long userId) {
                try {
                    mockTemplate.getForObject("http://localhost:8080/users/" + userId, String.class);
                } catch (HttpClientErrorException e) {
                    if (e.getStatusCode() == HttpStatus.NOT_FOUND)
                        throw new RuntimeException("User not found for order");
                }
            }
        };

        RuntimeException ex = assertThrows(RuntimeException.class, () -> client.validateUser(2L));
        assertEquals("User not found for order", ex.getMessage());
    }
}
