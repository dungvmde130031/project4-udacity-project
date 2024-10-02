package com.example.security;

import com.example.TestUtils;
import com.example.demo.infrastructure.security.UserDetailsServiceImpl;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserDetailsServiceImplTest {
    private final UserRepository userRepo = mock(UserRepository.class);
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Before
    public void setUp() {
        userDetailsServiceImpl = new UserDetailsServiceImpl(userRepo);
        TestUtils.injectObject(userDetailsServiceImpl, "userRepository", userRepo);
    }

    @Test
    public void testLoadUserByUsername() {
        String username = "trung";
        User user = new User();
        user.setUsername(username);
        String password = "password";
        user.setPassword(password);
        user.setId(0L);
        when(userRepo.findByUsername(username)).thenReturn(user);

        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);
        Assert.assertNotNull(userDetails);
        Collection<? extends GrantedAuthority> authorityCollection = userDetails.getAuthorities();
        Assert.assertNotNull(authorityCollection);
        Assert.assertEquals(0, authorityCollection.size());
        Assert.assertEquals(password, userDetails.getPassword());
        Assert.assertEquals(username, userDetails.getUsername());
    }
}