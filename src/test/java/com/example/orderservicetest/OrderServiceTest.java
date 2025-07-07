package com.example.orderservicetest;

import com.example.orderservice.entity.Order;
import com.example.orderservice.exception.OrderNotFoundException;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.service.OrderService;
import com.example.userservice.service.ExternalUserClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private OrderRepository repo;

    @Mock
    private ExternalUserClient userClient;

    @InjectMocks
    private OrderService orderService;

    private Order dummyOrder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dummyOrder = new Order();
        dummyOrder.setId(1L);
        dummyOrder.setUserId(100L);
        dummyOrder.setProduct("prod");
        dummyOrder.setQuantity(2);
        dummyOrder.setPrice(15.5);
    }

    @Test
    void testCreateOrder() {
        doNothing().when(userClient).validateUser(dummyOrder.getUserId());
        when(repo.save(dummyOrder)).thenReturn(dummyOrder);

        Order result = orderService.create(dummyOrder);

        assertEquals(dummyOrder, result);
        verify(userClient).validateUser(dummyOrder.getUserId());
        verify(repo).save(dummyOrder);
    }

    @Test
    void testGetOrderFound() {
        when(repo.findById(1L)).thenReturn(Optional.of(dummyOrder));

        Order result = orderService.get(1L);

        assertEquals(dummyOrder, result);
        verify(repo).findById(1L);
    }

    @Test
    void testGetOrderNotFound() {
        when(repo.findById(2L)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.get(2L));
    }

    @Test
    void testUpdateOrder() {
        Order updated = new Order();
        updated.setProduct("newprod");
        updated.setQuantity(3);
        updated.setPrice(30.0);

        when(repo.findById(1L)).thenReturn(Optional.of(dummyOrder));
        when(repo.save(any(Order.class))).thenAnswer(i -> i.getArgument(0));

        Order result = orderService.update(1L, updated);

        assertEquals(updated.getProduct(), result.getProduct());
        assertEquals(updated.getQuantity(), result.getQuantity());
        assertEquals(updated.getPrice(), result.getPrice());
        verify(repo).findById(1L);
        verify(repo).save(any(Order.class));
    }

    @Test
    void testDeleteOrder() {
        when(repo.findById(1L)).thenReturn(Optional.of(dummyOrder));
        doNothing().when(repo).delete(dummyOrder);

        orderService.delete(1L);

        verify(repo).findById(1L);
        verify(repo).delete(dummyOrder);
    }

    @Test
    void testDeleteOrderNotFound() {
        when(repo.findById(2L)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.delete(2L));
    }
}
