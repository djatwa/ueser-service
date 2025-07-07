package com.example.orderservicetest;

import com.example.orderservice.exception.OrderNotFoundException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OrderNotFoundExceptionTest {

    @Test
    void testMessageIsSetCorrectly() {
        Long id = 7L;
        OrderNotFoundException ex = new OrderNotFoundException(id);
        assertEquals("Order not found with id: " + id, ex.getMessage());
    }

    @Test
    void testIsRuntimeException() {
        OrderNotFoundException ex = new OrderNotFoundException(1L);
        assertTrue(ex instanceof RuntimeException);
    }
}
