package com.example.orderservicetest;

import com.example.orderservice.controller.OrderController;
import com.example.orderservice.entity.Order;
import com.example.orderservice.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class OrderControllerTest {
    @Mock
    private OrderService service;

    @InjectMocks
    private OrderController controller;

    private Order dummyOrder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dummyOrder = new Order();
        dummyOrder.setId(1L);
        dummyOrder.setUserId(100L);
        // set other fields as necessary
    }

    @Test
    void testCreate() {
        when(service.create(any(Order.class))).thenReturn(dummyOrder);

        ResponseEntity<Order> response = controller.create(dummyOrder);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(dummyOrder, response.getBody());
        verify(service).create(dummyOrder);
    }

    @Test
    void testGet() {
        when(service.get(1L)).thenReturn(dummyOrder);

        ResponseEntity<Order> response = controller.get(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dummyOrder, response.getBody());
        verify(service).get(1L);
    }

    @Test
    void testUpdate() {
        when(service.update(eq(1L), any(Order.class))).thenReturn(dummyOrder);

        ResponseEntity<Order> response = controller.update(1L, dummyOrder);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dummyOrder, response.getBody());
        verify(service).update(1L, dummyOrder);
    }

    @Test
    void testDelete() {
        doNothing().when(service).delete(1L);

        ResponseEntity<Void> response = controller.delete(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(service).delete(1L);
    }
}
