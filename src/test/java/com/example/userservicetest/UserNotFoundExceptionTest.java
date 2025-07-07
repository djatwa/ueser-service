package com.example.userservicetest;


import com.example.userservice.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserNotFoundExceptionTest {

    @Test
    void testMessageIsSetCorrectly() {
        Long id = 42L;
        UserNotFoundException ex = new UserNotFoundException(id);
        assertEquals("User not found with id " + id, ex.getMessage());
    }

    @Test
    void testIsRuntimeException() {
        UserNotFoundException ex = new UserNotFoundException(1L);
        assertTrue(ex instanceof RuntimeException);
    }
}
