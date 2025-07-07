package com.example.userservicetest;

import com.example.userservice.entity.User;
import com.example.userservice.exception.UserNotFoundException;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository repo;

    @InjectMocks
    private UserService userService;

    private User dummyUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dummyUser = new User();
        dummyUser.setId(1L);
        dummyUser.setUsername("testuser");
        dummyUser.setEmail("test@example.com");
        dummyUser.setPassword("pass");
    }

    @Test
    void testCreate() {
        when(repo.save(dummyUser)).thenReturn(dummyUser);

        User result = userService.create(dummyUser);

        assertEquals(dummyUser, result);
        verify(repo).save(dummyUser);
    }

    @Test
    void testGetFound() {
        when(repo.findById(1L)).thenReturn(Optional.of(dummyUser));

        User result = userService.get(1L);

        assertEquals(dummyUser, result);
        verify(repo).findById(1L);
    }

    @Test
    void testGetNotFound() {
        when(repo.findById(2L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.get(2L));
    }

    @Test
    void testUpdate() {
        User updated = new User();
        updated.setUsername("newuser");
        updated.setEmail("new@example.com");
        updated.setPassword("newpass");

        when(repo.findById(1L)).thenReturn(Optional.of(dummyUser));
        when(repo.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        User result = userService.update(1L, updated);

        assertEquals("newuser", result.getUsername());
        assertEquals("new@example.com", result.getEmail());
        assertEquals("newpass", result.getPassword());
        verify(repo).findById(1L);
        verify(repo).save(any(User.class));
    }

    @Test
    void testDelete() {
        when(repo.findById(1L)).thenReturn(Optional.of(dummyUser));
        doNothing().when(repo).delete(dummyUser);

        userService.delete(1L);

        verify(repo).findById(1L);
        verify(repo).delete(dummyUser);
    }

    @Test
    void testDeleteNotFound() {
        when(repo.findById(2L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.delete(2L));
    }
}
