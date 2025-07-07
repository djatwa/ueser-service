package com.example.userservicetest;


import com.example.userservice.controller.UserController;
import com.example.userservice.entity.User;
import com.example.userservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService service;

    @InjectMocks
    private UserController controller;

    private User dummyUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dummyUser = new User();
        dummyUser.setId(1L);
        dummyUser.setUsername("testuser");
        // set other fields as needed
    }

    @Test
    void testCreate() {
        when(service.create(any(User.class))).thenReturn(dummyUser);

        ResponseEntity<User> response = controller.create(dummyUser);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(dummyUser, response.getBody());
        verify(service).create(dummyUser);
    }

    @Test
    void testGet() {
        when(service.get(1L)).thenReturn(dummyUser);

        ResponseEntity<User> response = controller.get(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dummyUser, response.getBody());
        verify(service).get(1L);
    }

    @Test
    void testUpdate() {
        when(service.update(eq(1L), any(User.class))).thenReturn(dummyUser);

        ResponseEntity<User> response = controller.update(1L, dummyUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dummyUser, response.getBody());
        verify(service).update(1L, dummyUser);
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