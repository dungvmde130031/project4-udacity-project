package com.example.demo;

import com.example.demo.controllers.UserController;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
public class UserControllerTest {
    @InjectMocks
    private UserController userController;

    @Mock
    private final UserRepository users = mock(UserRepository.class);

    @Mock
    private final CartRepository cartRepository = mock(CartRepository.class);

    private final BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createUserHappyPath() throws Exception{
        // when(encoder.encode("Password")).thenReturn("HashedPassword");

        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setUsername("Username");
        userRequest.setPassword("Password");
        userRequest.setConfirmPassword("Password");

        ResponseEntity<User> response = userController.createUser(userRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        User user = response.getBody();
        assertNotNull(user);
        assertEquals(0, user.getId());
        assertEquals("Username", user.getUsername());
        // assertEquals("HashedPassword", user.getPassword());
    }

    @Test
    public void findByIdFoundTest(){
        User user = createUser();

        when(users.findById(1L)).thenReturn(Optional.of(user));
        ResponseEntity<User> response = userController.findById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void findByIdNotFoundTest(){
        ResponseEntity<User> response = userController.findById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void findByUsernameFoundTest(){
        User user = createUser();

        when(users.findByUsername("Username")).thenReturn(user);
        ResponseEntity<User> response = userController.findByUserName("Username");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
        assertEquals(1L, user.getId());
        assertEquals("Username", user.getUsername());
        assertEquals("Password", user.getPassword());
    }

    @Test
    public void findByUsernameNotFoundTest(){
        ResponseEntity<User> response = userController.findByUserName("Username");

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    public static User createUser(){
        User user = new User();

        user.setId(1);
        user.setUsername("Username");
        user.setPassword("Password");

        return user;
    }
}
